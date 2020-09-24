package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class CanceLateOrLeaveEarlyCommandHandler extends CommandHandler<CanceLateOrLeaveEarlyCommand> {

	@Inject
	 private ProcessCancel processCancel;
	@Override
	protected void handle(CommandHandlerContext<CanceLateOrLeaveEarlyCommand> context) {
		
		// processCancel.detailScreenCancelProcess(context.getCommand().getCompanyID(), context.getCommand().getAppID(), context.getCommand().getVersion());
		
	}

}
