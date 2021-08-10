package nts.uk.ctx.at.record.app.command.knr.knr002.L;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 *	切替日の個別登録する
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.APP.切替日の個別登録する.切替日の個別登録する
 */
@Stateless
public class RegisterSwitchingDateCommandHandler extends CommandHandler<RegisterSwitchingDatesCommand> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterSwitchingDatesCommand> context) {
		
		String datetimeFormat    			  = "yyyy/MM/dd HH:mm:ss";
		String contractCode       			  = AppContexts.user().contractCode();
		RegisterSwitchingDatesCommand command = context.getCommand();
		if (command.getTimeSwitchUKMode().equals("")) {
			this.timeRecordReqSettingRepository.updateSwitchDate(new ContractCode(contractCode), new EmpInfoTerminalCode(command.getEmpInfoTerminalCode()), null);
		} else {
			GeneralDateTime datetime 			  = GeneralDateTime.fromString(command.getTimeSwitchUKMode(), datetimeFormat);
			this.timeRecordReqSettingRepository.updateSwitchDate(new ContractCode(contractCode), new EmpInfoTerminalCode(command.getEmpInfoTerminalCode()), datetime);
		}
	}
}
