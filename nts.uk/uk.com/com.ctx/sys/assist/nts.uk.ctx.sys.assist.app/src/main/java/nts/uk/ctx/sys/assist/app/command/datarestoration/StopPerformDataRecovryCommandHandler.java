package nts.uk.ctx.sys.assist.app.command.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
@Stateless
public class StopPerformDataRecovryCommandHandler extends CommandHandler<StopPerformDataRecovryCommand> {

	@Inject
	private DataRecoveryMngRepository repoDataRecoveryMng;

	@Override
	protected void handle(CommandHandlerContext<StopPerformDataRecovryCommand> context) {
		StopPerformDataRecovryCommand updateCommand = context.getCommand();
		String dataRecoveryProcessId = updateCommand.getDataRecoveryProcessId();
		repoDataRecoveryMng.updateByOperatingCondition(dataRecoveryProcessId, DataRecoveryOperatingCondition.INTERRUPTION_END);
	}

}
