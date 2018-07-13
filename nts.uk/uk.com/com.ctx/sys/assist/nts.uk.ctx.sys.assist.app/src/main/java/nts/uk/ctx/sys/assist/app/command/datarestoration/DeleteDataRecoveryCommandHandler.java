package nts.uk.ctx.sys.assist.app.command.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
@Stateless
public class DeleteDataRecoveryCommandHandler extends CommandHandler<String> {

	@Inject
	private DataRecoveryMngRepository repoDataRecoveryMng;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		repoDataRecoveryMng.remove(context.getCommand());
	}

}