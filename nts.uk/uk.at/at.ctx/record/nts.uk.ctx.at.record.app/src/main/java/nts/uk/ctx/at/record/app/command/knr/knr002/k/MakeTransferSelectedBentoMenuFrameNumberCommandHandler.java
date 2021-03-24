package nts.uk.ctx.at.record.app.command.knr.knr002.k;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｋ：選択した弁当メニュー枠番を送信データにする
 * @author xuannt
 *
 */
@Stateless
public class MakeTransferSelectedBentoMenuFrameNumberCommandHandler extends CommandHandler<MakeTransferSelectedBentoMenuFrameNumberCommand>{
	
	//	選択した弁当メニュー枠番をUpdateするCommandHandler.handle
	@Inject
	UpdateSelectedBentoMenuFrameNumberCommandHandler updateSelectedBentoMenuFrameNumberCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<MakeTransferSelectedBentoMenuFrameNumberCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		MakeTransferSelectedBentoMenuFrameNumberCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode = new EmpInfoTerminalCode(command.getTerminalCode());
		List<Integer> selectedBentoMenuFrameNumbers = command.getSelectedBentoMenuFrameNumbers();
		//	1. 選択した弁当メニュー枠番を送信データにUpdateする(契約コード、就業情報端末コード、選択した弁当メニュー枠番<List>)
		this.updateSelectedBentoMenuFrameNumberCommandHandler
			.handle(new UpdateSelectedBentoMenuFrameNumberCommand(terminalCode, selectedBentoMenuFrameNumbers));
	}
}
