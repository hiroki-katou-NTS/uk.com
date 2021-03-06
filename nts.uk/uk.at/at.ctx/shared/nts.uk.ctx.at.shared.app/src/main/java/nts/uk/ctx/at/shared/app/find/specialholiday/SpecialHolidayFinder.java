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

		return this.sphdRepo.findByCompanyIdWithTargetItem(companyId).stream().map(c -> SpecialHolidayDto.fromDomain(c))
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

	public List<SpecialHolidayFrameDto> findForScreenJ(Integer selectedShCode) {
		List<SpecialHolidayFrameDto> result = new ArrayList<SpecialHolidayFrameDto>();
		// Group A
		// ?????????????????????????????????????????????????????????
		List<SpecialHolidayFrameDto> shFrames = this.shFrameFinder.findByUseCls();
		// ???????????????????????????????????????????????????
		List<SpecialHolidayFrameDto> absenceFrames = this.absenceFrameFinder.findByClassification().stream()
				.map(x -> SpecialHolidayFrameDto.fromAbsenDto(x)).collect(Collectors.toList());

		result.addAll(shFrames);
		result.addAll(absenceFrames);

		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("Msg_1267");
		}
		// group B
		// ?????????????????????????????????????????????????????????????????????????????????????????????
		List<SpecialHolidayEventDto> shEs = this.sHEventFinder.findAll();
		// ???????????????????????????????????????????????????????????????????????????
		String companyId = AppContexts.user().companyId();
		List<SpecialHolidayDto> shs = this.sphdRepo.findByCompanyIdWithTargetItem(companyId).stream()
				.map(c -> SpecialHolidayDto.fromDomain(c)).collect(Collectors.toList());
		// ???????????????????????????????????????????????????????????????????????????
		List<NursingLeaveSettingDto> nursings = this.nursingFinder.findNursingLeaveByCompanyId();

		result = removeAFromB(selectedShCode, result, shEs, shs, nursings);

		if (CollectionUtil.isEmpty(result)) {
			throw new BusinessException("Msg_1267");
		}
		return result;
	}

	private List<SpecialHolidayFrameDto> removeAFromB(Integer selectedShCode, List<SpecialHolidayFrameDto> result,
			List<SpecialHolidayEventDto> shEs, List<SpecialHolidayDto> shs, List<NursingLeaveSettingDto> nursings) {
		List<Integer> settingCodes = new ArrayList<Integer>();
		
		getDuplicateShEvent(settingCodes, shEs);
		
		// remove duplicate SHEvent
		result = result.stream().filter(x -> !(settingCodes.contains(x.getSpecialHdFrameNo())))
				.collect(Collectors.toList());

		result = removeDuplicateNursings(result, nursings);
		
		return removeDuplicateSHoliday(result, selectedShCode, shs);

	}

	private List<SpecialHolidayFrameDto> removeDuplicateNursings(List<SpecialHolidayFrameDto> result, List<NursingLeaveSettingDto> nursings) {
		
		List<Integer> absCodes = new ArrayList<Integer>();
		List<Integer> shCodes = new ArrayList<Integer>();
		if (!CollectionUtil.isEmpty(nursings)) {
			nursings.forEach(x -> {
				
				Integer absCode = x.absenceWorkDay;
				if (!absCodes.contains(absCode)) {
					absCodes.add(absCode);
				}
				
				Integer shCode = x.specialHolidayFrame;
				if (!shCodes.contains(shCode)) {
					shCodes.add(shCode);
				}
				
			});
		}
		
		result = result.stream().filter(x -> !isContains(x, shCodes, "a")).collect(Collectors.toList());

		result = result.stream().filter(x -> !isContains(x, absCodes, "b")).collect(Collectors.toList());
		
		return result;
	}

	private List<SpecialHolidayFrameDto> removeDuplicateSHoliday(List<SpecialHolidayFrameDto> result,
			Integer selectedShCode, List<SpecialHolidayDto> shs) {

		result = removeAbsFrameItem(selectedShCode, result, shs);

		result = removeFrameItem(selectedShCode, result, shs);
		return result;
	}

	private List<SpecialHolidayFrameDto> removeFrameItem(Integer selectedShCode, List<SpecialHolidayFrameDto> result,
			List<SpecialHolidayDto> shs) {
		String companyId = AppContexts.user().companyId();
		List<Integer> selectedframes = new ArrayList<Integer>();
		if (selectedShCode != null) {
			Optional<SpecialHoliday> specialHolidayOpt = this.sphdRepo.findBySingleCD(companyId, selectedShCode);
			specialHolidayOpt.ifPresent(x -> {
				selectedframes.addAll(x.getTargetItem().getFrameNo());
			});
		}
		List<Integer> registedItems = new ArrayList<Integer>();

		shs.forEach(x -> {
			registedItems.addAll(x.getTargetItemDto().getFrameNo());
		});

		List<Integer> fillerItemCodes = registedItems.stream().filter(x -> !selectedframes.contains(x))
				.collect(Collectors.toList());

		return result.stream().filter(x -> !isContains(x, fillerItemCodes, "a")).collect(Collectors.toList());
	}

	private List<SpecialHolidayFrameDto> removeAbsFrameItem(Integer selectedShCode, List<SpecialHolidayFrameDto> result,
			List<SpecialHolidayDto> shs) {
		String companyId = AppContexts.user().companyId();
		List<Integer> selectedAbsFrames = new ArrayList<Integer>();
		if (selectedShCode != null) {
			Optional<SpecialHoliday> specialHolidayOpt = this.sphdRepo.findBySingleCD(companyId, selectedShCode);
			specialHolidayOpt.ifPresent(x -> {
				selectedAbsFrames.addAll(x.getTargetItem().getAbsenceFrameNo());
			});
		}
		List<Integer> registedItems = new ArrayList<Integer>();

		shs.forEach(x -> {
			registedItems.addAll(x.getTargetItemDto().getAbsenceFrameNo());
		});

		List<Integer> fillerItemCodes = registedItems.stream().filter(x -> !selectedAbsFrames.contains(x))
				.collect(Collectors.toList());

		return result.stream().filter(x -> !isContains(x, fillerItemCodes, "b")).collect(Collectors.toList());

	}

	private boolean isContains(SpecialHolidayFrameDto x, List<Integer> fillerItemCodes, String itemType) {

		if (x.getItemType() == itemType && fillerItemCodes.contains(x.getSpecialHdFrameNo())) {
			return true;
		}
		return false;
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
