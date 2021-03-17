package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.knr.knr002.g.EmpTerminalRegisterInRequestCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.EmpTerminalRegisterInRequestCommandHandler;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
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
	//	就業情報端末のリクエスト一覧に登録するCommandHandler.handle
	@Inject
	private EmpTerminalRegisterInRequestCommandHandler empTerminalRegisterInRequestCommandHandler;
	/**
	 * @param empInforTerCode
	 * @return
	 */
	public DisplayTranferContentSettingsDto getTimeRecordReqSetting(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode terminalCode = new EmpInfoTerminalCode(empInforTerCode);
		//	1. get(契約コード、就業情報端末コード): 就業情報端末のリクエスト一覧
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTrRequest(terminalCode, contractCode);
		DisplayTranferContentSettingsDto dto = new DisplayTranferContentSettingsDto();
		TimeRecordReqSetting timeRecordReqSettingValue = null;
		//	2. G:就業情報端末のリクエスト一覧に登録する(契約コード、会社就業情報端末コード、会社ID、会社コード)
		if (!timeRecordReqSetting.isPresent()) {
			timeRecordReqSettingValue = new TimeRecordReqSetting.ReqSettingBuilder(
											terminalCode,
											contractCode,
											new CompanyId(AppContexts.user().companyId()),
											AppContexts.user().companyCode(),
											Collections.emptyList(),
											Collections.emptyList(),
											Collections.emptyList())
											.remoteSetting(false)	//	リモート設定　
											.sendEmployeeId(false)	//	社員ＩＤ送信
											.sendWorkType(false)	//	勤務種類コード送信　
											.sendWorkTime(false)	//	就業時間帯コード送信
											.overTimeHoliday(false)	//	残業・休日出勤送信　
											.applicationReason(false)	//	申請理由送信　
											.sendBentoMenu(false)	//	弁当メニュー枠番送信
											.timeSetting(false)	//	時刻セット　
											.reboot(false)	//	再起動を行う
											.stampReceive(false)	//	全ての打刻データ
											.applicationReceive(false)	//	全ての申請データ
											.reservationReceive(false)	//	全ての予約データ
										.workTime(Collections.emptyList())
										.build();
			//	
			this.empTerminalRegisterInRequestCommandHandler.handle(new EmpTerminalRegisterInRequestCommand(
																	timeRecordReqSettingValue.getTerminalCode(),
																	timeRecordReqSettingValue.isSendEmployeeId(),
																	timeRecordReqSettingValue.isSendWorkType(),
																	timeRecordReqSettingValue.isSendWorkTime(),
																	timeRecordReqSettingValue.isOverTimeHoliday(),
																	timeRecordReqSettingValue.isApplicationReason(),
																	timeRecordReqSettingValue.isSendBentoMenu(),
																	timeRecordReqSettingValue.isTimeSetting(),
																	timeRecordReqSettingValue.isReboot(), 
																	timeRecordReqSettingValue.isStampReceive(),
																	timeRecordReqSettingValue.isApplicationReceive(),
																	timeRecordReqSettingValue.isReservationReceive()));
		} else {
			timeRecordReqSettingValue = timeRecordReqSetting.get();
		}
		dto.setEmpInfoTerCode(timeRecordReqSettingValue.getTerminalCode().v());
		dto.setContractCode(timeRecordReqSettingValue.getContractCode().v());
		dto.setCompanyId(timeRecordReqSettingValue.getCompanyId().v());
		dto.setCompanyCode(timeRecordReqSettingValue.getCompanyCode());
		dto.setSendEmployeeId(timeRecordReqSettingValue.isSendEmployeeId());
		dto.setEmployeeIds(timeRecordReqSettingValue.getEmployeeIds()
				   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setSendBentoMenu(timeRecordReqSettingValue.isSendBentoMenu());
		dto.setBentoMenuFrameNumbers(timeRecordReqSettingValue.getBentoMenuFrameNumbers());
		dto.setSendWorkType(timeRecordReqSettingValue.isSendWorkType());
		dto.setWorkTypeCodes(timeRecordReqSettingValue.getWorkTypeCodes()
				   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setSendWorkTime(timeRecordReqSettingValue.isSendWorkTime());
		dto.setWorkTimeCodes(timeRecordReqSettingValue.getWorkTimeCodes()
						   .stream().map(e -> e.v()).collect(Collectors.toList()));
		dto.setOverTimeHoliday(timeRecordReqSettingValue.isOverTimeHoliday());
		dto.setApplicationReason(timeRecordReqSettingValue.isApplicationReason());
		dto.setStampReceive(timeRecordReqSettingValue.isStampReceive());
		dto.setReservationReceive(timeRecordReqSettingValue.isReservationReceive());
		dto.setApplicationReceive(timeRecordReqSettingValue.isApplicationReceive());
		dto.setTimeSetting(timeRecordReqSettingValue.isTimeSetting());
		dto.setRemoteSetting(timeRecordReqSettingValue.isRemoteSetting());
		dto.setReboot(timeRecordReqSettingValue.isReboot());
		return dto;
	}
}
