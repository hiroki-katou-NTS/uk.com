package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送受信内容の設定内容表示
 * 
 * @author xuannt
 *
 */
@Stateless
public class DisplayTranferContentSettings {
	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	public DisplayTranferContentSettingsDto getTimeRecordReqSetting(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReqSetting(new EmpInfoTerminalCode(empInforTerCode), contractCode);
		DisplayTranferContentSettingsDto dto = new DisplayTranferContentSettingsDto();
		if (!timeRecordReqSetting.isPresent())
			return dto;
		TimeRecordReqSetting timeRecordReqSettingValue = timeRecordReqSetting.get();
		dto.setEmpInfoTerCode(timeRecordReqSettingValue.getTerminalCode().v());
		dto.setCompanyId(timeRecordReqSettingValue.getCompanyId().v());
		dto.setCompanyCode(timeRecordReqSettingValue.getCompanyCode());
		dto.setContractCode(timeRecordReqSettingValue.getContractCode().v());
		dto.setBentoMenuFrameNumbers(timeRecordReqSettingValue.getBentoMenuFrameNumbers());
		dto.setEmployeeIds(timeRecordReqSettingValue.getEmployeeIds()
						   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setWorkTimeCodes(timeRecordReqSettingValue.getWorkTimeCodes()
						   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setWorkTypeCodes(timeRecordReqSettingValue.getWorkTypeCodes()
						   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setApplicationReason(timeRecordReqSettingValue.isApplicationReason());
		dto.setSendEmployeeId(timeRecordReqSettingValue.isSendEmployeeId());
		dto.setSendBentoMenu(timeRecordReqSettingValue.isSendBentoMenu());
		dto.setSendWorkType(timeRecordReqSettingValue.isSendWorkType());
		dto.setSendWorkTime(timeRecordReqSettingValue.isSendWorkTime());
		dto.setOverTimeHoliday(timeRecordReqSettingValue.isOverTimeHoliday());
		dto.setStampReceive(timeRecordReqSettingValue.isStampReceive());
		dto.setReservationReceive(timeRecordReqSettingValue.isReservationReceive());
		dto.setTimeSetting(timeRecordReqSettingValue.isTimeSetting());
		dto.setRemoteSetting(timeRecordReqSettingValue.isRemoteSetting());
		dto.setReboot(timeRecordReqSettingValue.isReboot());
		return dto;
	}
}
