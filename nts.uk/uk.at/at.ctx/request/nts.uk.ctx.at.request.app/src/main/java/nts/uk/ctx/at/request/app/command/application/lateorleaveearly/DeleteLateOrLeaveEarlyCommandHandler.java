package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;
import nts.uk.shr.com.context.AppContexts;

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
	
	@Override
	protected void handle(CommandHandlerContext<DeleteLateOrLeaveEarlyCommand> context) {
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();
		
		lateOrLeaveEarlyService.deleteLateOrLeaveEarly(companyID, appID);
		
	}

}
