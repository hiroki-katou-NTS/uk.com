package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.FactoryLateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.service.LateOrLeaveEarlyService;

@Stateless
@Transactional

public class CreateLateOrLeaveEarlyCommandHandler extends CommandHandler<CreateLateOrLeaveEarlyCommand> {

	@Inject
	private LateOrLeaveEarlyService lateOrLeaveEarlyService;
	
	@Inject
	private FactoryLateOrLeaveEarly factoryLateOrLeaveEarly;

	@Override
	protected void handle(CommandHandlerContext<CreateLateOrLeaveEarlyCommand> context) {
		CreateLateOrLeaveEarlyCommand command = context.getCommand();
        LateOrLeaveEarly domainLateOrLeaveEarly = factoryLateOrLeaveEarly.buildLateOrLeaveEarly(
        		command.getApplicationDate(),
        		command.getReasonTemp() +  System.lineSeparator() + command.getAppReason(),
        		command.getActualCancelAtr(),
        		command.getEarly1(),
        		command.getEarlyTime1(),
        		command.getLate1(),
        		command.getLateTime1(),
        		command.getEarly2(),
        		command.getEarlyTime2(),
        		command.getLate2(),
        		command.getLateTime2());

		lateOrLeaveEarlyService.createLateOrLeaveEarly(domainLateOrLeaveEarly);

	}
}
