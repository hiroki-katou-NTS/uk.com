package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyCommandHandler extends CommandHandler<LateLeaveEarlyCommand> {

	@Inject
	private LateLeaveEarlyService service;

	@Override
	protected void handle(CommandHandlerContext<LateLeaveEarlyCommand> context) {
		LateLeaveEarlyCommand command = context.getCommand();

		this.service.register(command.getAppType(), command.getInfoOutput(), command.getApplication());
	}

}
