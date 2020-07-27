package nts.uk.ctx.at.request.app.command.application.lateleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyCommandHandler extends CommandHandlerWithResult<LateLeaveEarlyCommand, ProcessResult> {

	@Inject
	private LateLeaveEarlyService service;

	@Override
	protected ProcessResult handle(CommandHandlerContext<LateLeaveEarlyCommand> context) {
		LateLeaveEarlyCommand command = context.getCommand();

		return this.service.register(command.getAppType(), command.getInfoOutput(), command.getApplication());
	}

}
