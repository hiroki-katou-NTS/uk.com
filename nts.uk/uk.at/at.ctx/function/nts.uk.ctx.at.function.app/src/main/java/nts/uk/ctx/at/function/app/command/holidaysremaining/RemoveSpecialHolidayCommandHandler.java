package nts.uk.ctx.at.function.app.command.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHoliday;

@Stateless
@Transactional
public class RemoveSpecialHolidayCommandHandler extends CommandHandler<SpecialHolidayCommand>
{
    
    @Inject
    private SpecialHoliday removeSpecialHoliday;
    
    @Override
    protected void handle(CommandHandlerContext<SpecialHolidayCommand> context) {
    }
}
