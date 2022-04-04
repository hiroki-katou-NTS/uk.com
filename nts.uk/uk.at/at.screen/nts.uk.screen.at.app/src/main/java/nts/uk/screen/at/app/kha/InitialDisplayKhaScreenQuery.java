package nts.uk.screen.at.app.kha;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Screen Query - 初期表示を行う
 * 
 * @author NWS
 *
 */
@Stateless
public class InitialDisplayKhaScreenQuery {
	@Inject
	private SupportOperationSettingRepository sosRepository;

	@Inject
	private JudCriteriaSameStampOfSupportRepo judCriteriaSameStampOfSupportRepo;

	@Inject
	private SupportWorkSettingRepository supportWorkSettingRepository;

	public InitKha001DisplayDto get(String cid) {
		// Step 1: get 応援の運用設定
		SupportOperationSetting domain = sosRepository.get(cid);
		SupportOperationSettingDto supportOperationSettingDto = new SupportOperationSettingDto(domain.isUsed(),
				domain.isSupportDestinationCanSpecifySupporter(), domain.getMaxNumberOfSupportOfDay().v());

		// Step 2: get 応援の同一打刻の判断基準
		JudgmentCriteriaSameStampOfSupport judgmentCriteriaSameStampOfSupport = judCriteriaSameStampOfSupportRepo
				.get(cid);
		JudgmentCriteriaSameStampOfSupportDto judgmentCriteriaSameStampOfSupportDto = new JudgmentCriteriaSameStampOfSupportDto();
		if (judgmentCriteriaSameStampOfSupport != null) {
			judgmentCriteriaSameStampOfSupportDto = new JudgmentCriteriaSameStampOfSupportDto(
					judgmentCriteriaSameStampOfSupport.getSameStampRanceInMinutes().v());
		}

		// Step 3: get 応援・作業設定
		SupportWorkSetting supportWorkSetting = supportWorkSettingRepository.get(cid);
		SupportWorkSettingDto supportWorkSettingDto = new SupportWorkSettingDto();
		if (supportWorkSetting != null) {
			supportWorkSettingDto = new SupportWorkSettingDto(domain.isUsed(),
					supportWorkSetting.getAccountingOfMoveTime().value);
		}
		InitKha001DisplayDto displayDto = new InitKha001DisplayDto(supportOperationSettingDto,
				judgmentCriteriaSameStampOfSupportDto, supportWorkSettingDto);
		return displayDto;
	}

}
