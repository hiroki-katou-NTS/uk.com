package nts.uk.ctx.at.record.app.command.knr.knr002.g;
/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｇ：選択した勤務種類を送信データにする（Ｉデータ登録）
 * @author xuannt
 *
 */

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MakeTransferSelectedWorkTypesCommandHandler extends CommandHandler<MakeTransferSelectedWorkTypesCommand>{
	//	選択した勤務種類を登録する（Ｉデータ登録）CommandHandler.handle
	@Inject
	RegisterSelectedWorkTypesCommandHandler registerSelectedWorkTypeCommandHandler;

	@Override
	protected void handle(CommandHandlerContext<MakeTransferSelectedWorkTypesCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		MakeTransferSelectedWorkTypesCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode = new EmpInfoTerminalCode(command.getTerminalCode());
		List<WorkTypeCode> selectedWorkTypes = command.getSelectedWorkTypes().stream()
											   .map(e -> new WorkTypeCode(e)).collect(Collectors.toList());
		//	1. 選択した勤務種類を送信データにUpdateする(契約コード、就業情報端末コード、選択した勤務種類コード<List>)
		this.registerSelectedWorkTypeCommandHandler
			.handle(new RegisterSelectedWorkTypesCommand(terminalCode, selectedWorkTypes));
	}
	

}
