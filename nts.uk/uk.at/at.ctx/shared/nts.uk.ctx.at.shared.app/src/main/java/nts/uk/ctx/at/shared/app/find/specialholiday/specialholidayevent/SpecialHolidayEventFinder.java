package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;
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

	public List<SpecialHolidayFrameWithSettingDto> getFrames() {

		List<SpecialHolidayFrameWithSettingDto> result;

		String companyId = AppContexts.user().companyId();
		// ドメインモデル「特別休暇枠」を取得する(Lấy domain 「SpecialHolidayFrame - 特別休暇枠」)
		List<SpecialHolidayFrame> sHFrames = this.sHFrameRepo.findSpecialHolidayFrame(companyId);

		if (CollectionUtil.isEmpty(sHFrames)) {

			throw new BusinessException("Msg_1337");

		}

		/// ドメインモデル「特別休暇．対象項目．対象の特別休暇枠」を取得する(lấy domain 「特別休暇．対象項目．対象の特別休暇枠」)
		List<SpecialHoliday> sHs = sHRepo.findByCompanyId(companyId);
		// AからBを取り除く(Remove B khỏi A)
		List<Integer> sHsNos = new ArrayList<Integer>();
		sHs.forEach(x -> {
			if (x.getTargetItem() != null) {
				sHsNos.addAll(x.getTargetItem().getFrameNo());
			}
		});

		sHFrames = sHFrames.stream().filter(x -> !sHsNos.contains(x.getSpecialHdFrameNo()))
				.collect(Collectors.toList());

		if (CollectionUtil.isEmpty(sHFrames)) {

			throw new BusinessException("Msg_1337");

		}
		// ドメインモデル「事象に対する特別休暇」を取得する(lấy thông tin domain 「事象別に対する特別休暇」)
		List<SpecialHolidayEvent> sHEvents = this.sHEventRepo.findByCompanyIdAndNoLst(companyId, sHsNos);

		// 特別休暇枠一覧の設定済をすべてクリアにする(Clear all các setting đã set cho list
		// specialHolidayFrame)
		boolean isSetting = false;
		result = sHFrames.stream().map(x -> SpecialHolidayFrameWithSettingDto.fromDomain(x, isSetting))
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(sHEvents)) {
			// 設定済表示処理 (Xứ lý hiển thị đã được setting)
			setSettingFrames(result, sHsNos);
		}
		return result;
	}

	private void setSettingFrames(List<SpecialHolidayFrameWithSettingDto> frameSettings, List<Integer> sHsNos) {
		frameSettings.forEach(x -> {
			if (sHsNos.contains(x.getSpecialHdFrameNo()))
				x.setSetting(true);
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

}
