package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.text.ParseException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.recoverystorage.RecoveryStorageService;

@Stateless
public class RecoveryStogareAsysnCommandHandler extends AsyncCommandHandler<PerformDataRecoveryCommand> {
	@Inject
	private RecoveryStorageService recoveryStorageService;

	@Override
	public void handle(CommandHandlerContext<PerformDataRecoveryCommand> context) {
		PerformDataRecoveryCommand performDataCommand = context.getCommand();
		String dataRecoveryProcessId = performDataCommand.recoveryProcessingId;
		// サーバー復旧処理
		try {
			recoveryStorageService.recoveryStorage(dataRecoveryProcessId);
		} catch (ParseException e) {
			//Todo
		}
	}
}
