package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyCommandHandler extends CommandHandler<LateLeaveEarlyCommand> {

	@Inject
	private LateLeaveEarlyRepository repository;

	@Override
	protected void handle(CommandHandlerContext<LateLeaveEarlyCommand> context) {
		LateLeaveEarlyCommand command = context.getCommand();

		this.repository.register(command.getAppType(), command.getInfoOutput(), command.getApplication());
	}

}
