package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecialHolidayEventFinder {

	@Inject
	private SpecialHolidayFrameRepository sHFrameRepo;

	@Inject
	private SpecialHolidayRepository sHRepo;

	@Inject
	private SpecialHolidayEventRepository sHEventRepo;

	@Inject
	private NursingLeaveSettingRepository nurRepo;

	public List<SpecialHolidayFrameWithSettingDto> getFrames() {

		List<SpecialHolidayFrameWithSettingDto> result;

		String companyId = AppContexts.user().companyId();
		// ドメインモデル「特別休暇枠」を取得する(Lấy domain 「SpecialHolidayFrame - 特別休暇枠」)
		List<SpecialHolidayFrame> sHFrames = this.sHFrameRepo.findSpecialHolidayFrame(companyId);

		if (CollectionUtil.isEmpty(sHFrames)) {

			throw new BusinessException("Msg_1337");

		}
		List<Integer> hasSettingNos = new ArrayList<Integer>();
		/// ドメインモデル「特別休暇．対象項目．対象の特別休暇枠」を取得する(lấy domain 「特別休暇．対象項目．対象の特別休暇枠」)
		addSpecialNos(companyId, hasSettingNos);
		// ドメインモデル「介護看護休暇設定．特別休暇枠」「介護看護休暇設定．欠勤枠」を取得する
		addNursingNos(companyId, hasSettingNos);

		// AからBを取り除く(Remove B khỏi A)
		sHFrames = sHFrames.stream().filter(x -> !hasSettingNos.contains(x.getSpecialHdFrameNo()))
				.collect(Collectors.toList());

		if (CollectionUtil.isEmpty(sHFrames)) {

			throw new BusinessException("Msg_1337");

		}

		List<Integer> NoSeetingSHsNos = sHFrames.stream().map(x -> x.getSpecialHdFrameNo())
				.collect(Collectors.toList());

		// ドメインモデル「事象に対する特別休暇」を取得する(lấy thông tin domain 「事象別に対する特別休暇」)
		List<SpecialHolidayEvent> sHEvents = this.sHEventRepo.findByCompanyIdAndNoLst(companyId, NoSeetingSHsNos);

		// 特別休暇枠一覧の設定済をすべてクリアにする(Clear all các setting đã set cho list
		// specialHolidayFrame)
		boolean isSetting = false;
		result = sHFrames.stream().map(x -> SpecialHolidayFrameWithSettingDto.fromDomain(x, isSetting))
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(sHEvents)) {
			// 設定済表示処理 (Xứ lý hiển thị đã được setting)
			setSettingFrames(result, sHEvents);
		}
		return result;
	}

	private void addSpecialNos(String companyId, List<Integer> hasSettingNos) {
		List<SpecialHoliday> sHs = sHRepo.findByCompanyIdWithTargetItem(companyId);
		sHs.forEach(x -> {
			if (x.getTargetItem() != null) {
				List<Integer> fNos = x.getTargetItem().getFrameNo();
				if (!CollectionUtil.isEmpty(fNos)) {
					fNos.forEach(no -> {
						if (!hasSettingNos.contains(no)) {
							hasSettingNos.add(no);
						}
					});
				}
			}
		});

	}

	private void addNursingNos(String companyId, List<Integer> hasSettingNos) {
		List<NursingLeaveSetting> nurSettings = this.nurRepo.findByCompanyId(companyId);
		if (nurSettings != null) {
			nurSettings.forEach(x -> {
				if (x.getSpecialHolidayFrame() != null) {
					x.getSpecialHolidayFrame().ifPresent(value -> {
						hasSettingNos.add(value);
					});
				}
			});
		}
	}

	private void setSettingFrames(List<SpecialHolidayFrameWithSettingDto> frameSettings,
			List<SpecialHolidayEvent> sHEvents) {
		sHEvents.forEach(x -> {
			Optional<SpecialHolidayFrameWithSettingDto> itemOpt = frameSettings.stream()
					.filter(frame -> frame.getSpecialHdFrameNo() == x.getSpecialHolidayEventNo()).findFirst();
			itemOpt.ifPresent(item -> {
				item.setSetting(true);
			});
		});

	}

	public SpecialHolidayEventDto changeSpecialEvent(int noSelected) {

		String companyId = AppContexts.user().companyId();
		// ドメインモデル「事象別に対する特別休暇」をすべて取得する(lấy dữ liệu domain「特別休暇」)
		Optional<SpecialHolidayEvent> sHEventOpt = this.sHEventRepo.findByKey(companyId, noSelected);

		if (sHEventOpt.isPresent()) {
			return SpecialHolidayEventDto.fromDomain(sHEventOpt.get());
		}
		return null;
	}

	public List<SpecialHolidayEventDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.sHEventRepo.findByCompany(companyId).stream().map(x -> SpecialHolidayEventDto.fromDomain(x))
				.collect(Collectors.toList());
	}

}
