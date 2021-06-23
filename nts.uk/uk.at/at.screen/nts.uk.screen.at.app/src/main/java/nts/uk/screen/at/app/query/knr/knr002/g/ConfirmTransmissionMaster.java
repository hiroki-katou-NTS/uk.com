package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
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
																  .getTrRequest(empInforTerCode, contractCode);
		
		ConfirmTransmissionMasterDto dto = new ConfirmTransmissionMasterDto();
		if (!timeRecordReqSetting.isPresent())
			return dto;
	    
		Optional<TimeRecordReqSetting> timeRecordEmployee = this.timeRecordReqSettingRepository.getTimeRecordEmployee(empInforTerCode, contractCode);
		Optional<TimeRecordReqSetting> timeRecordWorkType = this.timeRecordReqSettingRepository.getTimeRecordWorkType(empInforTerCode, contractCode);
		Optional<TimeRecordReqSetting> timeRecordWorkTime = this.timeRecordReqSettingRepository.getTimeRecordWorkTime(empInforTerCode, contractCode);
		Optional<TimeRecordReqSetting> timeRecordReservation = this.timeRecordReqSettingRepository.getTimeRecordReservation(empInforTerCode, contractCode);
				
		TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
		dto.setEmpInfoTerCode(timeRecordReqSettingVal.getTerminalCode().v());
		dto.setSendEmployeeId(timeRecordReqSettingVal.isSendEmployeeId());
		dto.setEmployeeIds(timeRecordEmployee.isPresent() ? timeRecordEmployee.get().getEmployeeIds().stream().map(e -> e.v()).collect(Collectors.toList()) : Collections.emptyList());
		dto.setSendBentoMenu(timeRecordReqSettingVal.isSendBentoMenu());
		dto.setBentoMenuFrameNumbers(timeRecordReservation.isPresent() ? timeRecordReservation.get().getBentoMenuFrameNumbers() : Collections.emptyList());
		dto.setSendWorkType(timeRecordReqSettingVal.isSendWorkType());
		dto.setWorkTypeCodes(timeRecordWorkType.isPresent() ? timeRecordWorkType.get().getWorkTypeCodes().stream().map(e -> e.v()).collect(Collectors.toList()): Collections.emptyList());
		dto.setSendWorkTime(timeRecordReqSettingVal.isSendWorkTime());
		dto.setWorkTimeCodes(timeRecordWorkTime.isPresent()? timeRecordWorkTime.get().getWorkTimeCodes().stream().map(e -> e.v()).collect(Collectors.toList()): Collections.emptyList());
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
