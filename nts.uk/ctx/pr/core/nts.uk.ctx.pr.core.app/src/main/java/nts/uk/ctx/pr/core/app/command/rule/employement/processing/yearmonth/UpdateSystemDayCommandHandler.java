package nts.uk.ctx.pr.core.app.command.rule.employement.processing.yearmonth;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateSystemDayCommandHandler extends CommandHandler<UpdateSystemDayCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdateSystemDayCommand> context) {
		
	}

}
