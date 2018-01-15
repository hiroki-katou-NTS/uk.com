package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class DeleteLateOrLeaveEarlyCommandHandler extends CommandHandler<DeleteLateOrLeaveEarlyCommand> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;
	
	@Inject
	private AfterProcessDelete afterProcessDeleteService;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteLateOrLeaveEarlyCommand> context) {
	
		afterProcessDeleteService.screenAfterDelete(context.getCommand().getCompanyID(), context.getCommand().getAppID(), context.getCommand().getVersion());
		lateOrLeaveEarlyService.deleteLateOrLeaveEarly(context.getCommand().getCompanyID(), context.getCommand().getAppID());
		
		
	}

}
