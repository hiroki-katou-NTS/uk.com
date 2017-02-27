package nts.uk.ctx.pr.core.app.command.rule.employement.processing.yearmonth;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class CreatePaydayProcessingCommandHandler extends CommandHandler<CreatePaydayProcessingCommand> {

	@Override
	protected void handle(CommandHandlerContext<CreatePaydayProcessingCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
