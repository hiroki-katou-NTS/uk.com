package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class DeleteLateOrLeaveEarlyCommandHandler extends CommandHandlerWithResult<DeleteLateOrLeaveEarlyCommand, ProcessResult> {
	
	@Inject
	private AfterProcessDelete afterProcessDeleteService;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<DeleteLateOrLeaveEarlyCommand> context) {
		// lateOrLeaveEarlyService.deleteLateOrLeaveEarly(context.getCommand().getCompanyID(), context.getCommand().getAppID());
		return afterProcessDeleteService.screenAfterDelete(
				context.getCommand().getCompanyID(), 
				context.getCommand().getAppID(), 
				context.getCommand().getVersion())
				.getProcessResult();
	}

}
