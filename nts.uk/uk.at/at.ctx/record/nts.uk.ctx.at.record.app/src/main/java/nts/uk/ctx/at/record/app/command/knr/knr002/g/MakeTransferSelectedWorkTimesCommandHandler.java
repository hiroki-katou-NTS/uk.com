package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｇ：選択した就業時間帯を送信データにする（Ｊデータ登録）
 * @author xuannt
 *
 */
@Stateless
public class MakeTransferSelectedWorkTimesCommandHandler extends CommandHandler<MakeTransferSelectedWorkTimesCommand>{
	//	選択した就業時間帯をUpdateする（Ｊデータ登録）CommandHandler.handle
	@Inject
	UpdateSelectedWorkTimesCommandHandler updateSelectedWorkTimesCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<MakeTransferSelectedWorkTimesCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		MakeTransferSelectedWorkTimesCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode = new EmpInfoTerminalCode(command.getTerminalCode());
		List<WorkTimeCode> selectedWorkTimes = command.getSelectedWorkTimes().stream()
											   .map(e -> new WorkTimeCode(e)).collect(Collectors.toList());
		//	1. 選択した勤務種類を送信データにUpdateする(契約コード、就業情報端末コード、選択した就業時間帯のコード<List>)
		this.updateSelectedWorkTimesCommandHandler.handle(new UpdateSelectedWorkTimesCommand(terminalCode, selectedWorkTimes));

	}
}
