package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｇ：就業情報端末のリクエスト一覧に登録する
 * @author xuannt
 *
 */
@Stateless
public class EmpTerminalRegisterInRequestCommandHandler extends CommandHandler<EmpTerminalRegisterInRequestCommand>{

	//	就業情報端末のリクエスト一覧Repository.[1]  就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Override
	protected void handle(CommandHandlerContext<EmpTerminalRegisterInRequestCommand> context) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpTerminalRegisterInRequestCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode = command.getTerminalCode();
		//	1. get*(契約コード、就業情報端末コード)
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReqSetting(terminalCode, contractCode);
		if(!timeRecordReqSetting.isPresent()) {
			//	2. create(契約コード、就業情報端末コード、就業情報端末のリクエスト一覧）
			TimeRecordReqSetting timeRecordReqSettingRegist = new TimeRecordReqSetting.ReqSettingBuilder(
																	command.getTerminalCode(),
																	contractCode,
																	new CompanyId(AppContexts.user().companyId()),
																	AppContexts.user().companyCode(),
																	null,
																	null,
																	null)
																.build();
			//	4. persist
			this.timeRecordReqSettingRepository.insert(timeRecordReqSettingRegist);
		} else {
			TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
			//	3. set(契約コード、就業情報端末コード、就業情報端末のリクエスト一覧)
			TimeRecordReqSetting timeRecordReqSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
																		timeRecordReqSettingVal.getTerminalCode(),
																		timeRecordReqSettingVal.getContractCode(),
																		timeRecordReqSettingVal.getCompanyId(),
																		timeRecordReqSettingVal.getCompanyCode(),
																		timeRecordReqSettingVal.getEmployeeIds(),
																		timeRecordReqSettingVal.getBentoMenuFrameNumbers(),
																		timeRecordReqSettingVal.getWorkTypeCodes())
																	.sendEmployeeId(command.isSendEmployeeId())
																	.sendWorkType(command.isSendWorkType())
																	.sendWorkTime(command.isSendWorkTime())
																	.overTimeHoliday(command.isOverTimeHoliday())
																	.applicationReason(command.isApplicationReason())
																	.sendBentoMenu(command.isSendBentoMenu())
																	.timeSetting(command.isTimeSetting())
																	.reboot(command.isReboot())
																	.stampReceive(command.isStampReceive())
																	.applicationReceive(command.isApplicationReceive())
																	.reservationReceive(command.isReservationReceive())
															  .build();
			//	4. persist
			this.timeRecordReqSettingRepository.updateSetting(timeRecordReqSettingUpdate);
			}
		}

}
