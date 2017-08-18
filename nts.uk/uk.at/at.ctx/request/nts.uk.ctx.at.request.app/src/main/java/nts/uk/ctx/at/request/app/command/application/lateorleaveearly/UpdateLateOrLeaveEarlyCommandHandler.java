package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class UpdateLateOrLeaveEarlyCommandHandler extends CommandHandler<UpdateLateOrLeaveEarlyCommand> {
	
	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;
	@Override
	protected void handle(CommandHandlerContext<UpdateLateOrLeaveEarlyCommand> context) {
	//	UpdateLateOrLeaveEarlyCommand command = context.getCommand();
		String appID = IdentifierUtil.randomUniqueId();
		lateOrLeaveEarlyRepository.update(context.getCommand().toDomain(appID ));
		
	}
	

}
