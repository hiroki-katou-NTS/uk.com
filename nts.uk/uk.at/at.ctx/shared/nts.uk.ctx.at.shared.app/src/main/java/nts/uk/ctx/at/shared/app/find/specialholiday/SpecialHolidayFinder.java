package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.NursingLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameFinder;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameDto;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameFinder;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class SpecialHolidayFinder {
	@Inject
	private SpecialHolidayRepository sphdRepo;
	@Inject
	private SpecialHolidayEventFinder sHEventFinder;
	@Inject
	private NursingLeaveFinder nursingFinder;
	@Inject
	private AbsenceFrameFinder absenceFrameFinder;
	@Inject
	private SpecialHolidayFrameFinder shFrameFinder;

	/**
	 * Get List Special Holiday by companyId
	 * 
	 * @return
	 */
	public List<SpecialHolidayDto> findByCompanyId() {
		String companyId = AppContexts.user().companyId();

		return this.sphdRepo.findByCompanyId(companyId).stream().map(c -> SpecialHolidayDto.fromDomain(c))
				.collect(Collectors.toList());
	}

	/**
	 * Get Special Holiday by sphdCd
	 * 
	 * @param specialHolidayCode
	 * @return
	 */
	public SpecialHolidayDto getSpecialHoliday(int specialHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<SpecialHoliday> data = sphdRepo.findBySingleCD(companyId, specialHolidayCode);

		if (data.isPresent()) {
			return SpecialHolidayDto.fromDomain(data.get());
		}

		return null;
	}

	public List<SpecialHolidayFrameDto> findForScreenJ(Integer specialHolidayCode) {
		List<SpecialHolidayFrameDto> result = new ArrayList<SpecialHolidayFrameDto>();
		// Group A
		// ドメインモデル「特別休暇枠」を取得する
		List<SpecialHolidayFrameDto> shFrames = this.shFrameFinder.findAll();
		// ドメインモデル「欠勤枠」を取得する
		List<SpecialHolidayFrameDto> absenceFrames = this.absenceFrameFinder.findAll().stream()
				.map(x -> SpecialHolidayFrameDto.fromAbsenDto(x)).collect(Collectors.toList());

		result.addAll(shFrames);
		result.addAll(absenceFrames);

		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("Msg_1267");
		}
		// group B
		// ドメインモデル「事象に対する特別休暇．対象項目」をチェックする
		List<SpecialHolidayEventDto> shEs = this.sHEventFinder.findAll();
		// ドメインモデル「特別休暇．対象項目」をチェックする
		String companyId = AppContexts.user().companyId();
		List<SpecialHolidayDto> shs = this.sphdRepo.findByCompanyIdWithTargetItem(companyId).stream()
				.map(c -> SpecialHolidayDto.fromDomain(c)).collect(Collectors.toList());
		// ドメインモデル「特別休暇．対象項目」をチェックする
		List<NursingLeaveSettingDto> nursings = this.nursingFinder.findNursingLeaveByCompanyId();
	
		List<Integer> selectedItems = getSelectedSpecialHolidayItems(companyId, specialHolidayCode);
		
		result = removeAFromB(selectedItems, result, shEs, shs, nursings);

		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("Msg_1267");
		}
		return result;
	}

	private List<Integer> getSelectedSpecialHolidayItems(String companyId, Integer specialHolidayCode) {
		List<Integer> returnList = new ArrayList<Integer>();
		if (specialHolidayCode != null) {
			Optional<SpecialHoliday> specialHolidayOpt = this.sphdRepo.findBySingleCD(companyId, specialHolidayCode);
			if (specialHolidayOpt.isPresent()) {

				SpecialHoliday specialHoliday = specialHolidayOpt.get();
				if (specialHoliday.getTargetItem() != null) {
					List<Integer> absNos = specialHoliday.getTargetItem().getAbsenceFrameNo();
					if (!CollectionUtil.isEmpty(absNos)) {
						returnList.addAll(absNos);
					}

					List<Integer> fNos = specialHoliday.getTargetItem().getFrameNo();
					if (!CollectionUtil.isEmpty(fNos)) {
						returnList.addAll(fNos);
					}
				}

			}
		}
		return returnList;
	}

	private List<SpecialHolidayFrameDto> removeAFromB(List<Integer> selectedItems, List<SpecialHolidayFrameDto> result,
			List<SpecialHolidayEventDto> shEs, List<SpecialHolidayDto> shs, List<NursingLeaveSettingDto> nursings) {
		List<Integer> settingCodes = new ArrayList<Integer>();
		getDuplicateShEvent(settingCodes, shEs);
		getDuplicateSHoliday(selectedItems, settingCodes, shs);
		getDuplicateNursings(settingCodes, nursings);

		return result.stream().filter(x -> !(settingCodes.contains(x.getSpecialHdFrameNo())))
				.collect(Collectors.toList());
	}

	private List<Integer> getDuplicateNursings(List<Integer> settingCodes, List<NursingLeaveSettingDto> nursings) {
		if (!CollectionUtil.isEmpty(nursings)) {
			nursings.forEach(x -> {
				Integer code = x.specialHolidayFrame;
				if (!settingCodes.contains(code)) {
					settingCodes.add(code);
				}
			});
		}
		return settingCodes;
	}

	private List<Integer> getDuplicateSHoliday(List<Integer> selectedItems, List<Integer> settingCodes,
			List<SpecialHolidayDto> shs) {
			shs.forEach(x -> {
				if (x.getTargetItemDto() != null) {
					List<Integer> absFrames = x.getTargetItemDto().getAbsenceFrameNo();
					if (!CollectionUtil.isEmpty(absFrames)) {
						absFrames.forEach(code -> {
							if (!settingCodes.contains(code) && !selectedItems.contains(code)) {
								settingCodes.add(code);
							}
						});
					}
					List<Integer> frames = x.getTargetItemDto().getFrameNo();
					if (!CollectionUtil.isEmpty(frames)) {
						frames.forEach(code -> {
							if (!settingCodes.contains(code) && !selectedItems.contains(code)) {
								settingCodes.add(code);
							}
						});
					}
				}
			});
		

		return settingCodes;
	}

	private List<Integer> getDuplicateShEvent(List<Integer> settingCodes, List<SpecialHolidayEventDto> shEs) {
		shEs.forEach(x -> {
			Integer code = x.getSpecialHolidayEventNo();
			if (!settingCodes.contains(code)) {
				settingCodes.add(code);
			}
		});
		return settingCodes;
	}

	public List<SpecialHolidayFrameDto> findAllItemFrame() {
		List<SpecialHolidayFrameDto> result = new ArrayList<SpecialHolidayFrameDto>();
		List<SpecialHolidayFrameDto> shFrames = this.shFrameFinder.findAll();
		List<SpecialHolidayFrameDto> absenceFrames = this.absenceFrameFinder.findAll().stream()
				.map(x -> SpecialHolidayFrameDto.fromAbsenDto(x)).collect(Collectors.toList());

		result.addAll(shFrames);
		result.addAll(absenceFrames);
		
		return result;
	}
}
