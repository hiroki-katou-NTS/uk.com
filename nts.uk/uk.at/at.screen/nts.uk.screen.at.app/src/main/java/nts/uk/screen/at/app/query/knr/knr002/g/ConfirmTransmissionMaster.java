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
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送信マスタの確認
 * 
 * @author xuannt
 *
 */
@Stateless
public class ConfirmTransmissionMaster {

	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	// 1. get*(契約コード、就業情報端末コード): 就業情報端末のリクエスト一覧
	/**
	 * @param terminalCode
	 * @return
	 */
	public ConfirmTransmissionMasterDto getTimeRecordReqSetting(String terminalCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInforTerCode = new EmpInfoTerminalCode(terminalCode);
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReqSetting(empInforTerCode, contractCode);
		ConfirmTransmissionMasterDto dto = new ConfirmTransmissionMasterDto();
		if (!timeRecordReqSetting.isPresent())
			return dto;
		TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
		dto.setEmpInfoTerCode(timeRecordReqSettingVal.getTerminalCode().v());
		dto.setSendEmployeeId(timeRecordReqSettingVal.isSendEmployeeId());
		dto.setEmployeeIds(timeRecordReqSettingVal.getEmployeeIds().stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setSendBentoMenu(timeRecordReqSettingVal.isSendBentoMenu());
		dto.setBentoMenuFrameNumbers(timeRecordReqSettingVal.getBentoMenuFrameNumbers());
		dto.setSendWorkType(timeRecordReqSettingVal.isSendWorkType());
		dto.setWorkTypeCodes(timeRecordReqSettingVal.getWorkTypeCodes().stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setSendWorkTime(timeRecordReqSettingVal.isSendWorkTime());
		dto.setWorkTimeCodes(timeRecordReqSettingVal.getWorkTimeCodes().stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setOverTimeHoliday(timeRecordReqSettingVal.isOverTimeHoliday());
		dto.setApplicationReason(timeRecordReqSettingVal.isApplicationReason());
		dto.setStampReceive(timeRecordReqSettingVal.isStampReceive());
		dto.setReservationReceive(timeRecordReqSettingVal.isReservationReceive());
		dto.setApplicationReceive(timeRecordReqSettingVal.isApplicationReceive());
		dto.setTimeSetting(timeRecordReqSettingVal.isTimeSetting());
		dto.setRemoteSetting(timeRecordReqSettingVal.isRemoteSetting());
		dto.setReboot(timeRecordReqSettingVal.isReboot());
		return dto;
	}
}
