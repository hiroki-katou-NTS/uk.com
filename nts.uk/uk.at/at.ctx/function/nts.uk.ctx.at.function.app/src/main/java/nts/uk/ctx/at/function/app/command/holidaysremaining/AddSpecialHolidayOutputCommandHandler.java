package nts.uk.ctx.at.function.app.command.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class AddSpecialHolidayOutputCommandHandler extends CommandHandler<SpecialHolidayOutputCommand>
{
	@Override
    protected void handle(CommandHandlerContext<SpecialHolidayOutputCommand> context) {
    	
    }
}
