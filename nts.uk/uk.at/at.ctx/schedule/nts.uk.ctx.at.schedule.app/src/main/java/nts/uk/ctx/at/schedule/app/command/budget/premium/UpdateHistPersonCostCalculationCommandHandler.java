package nts.uk.ctx.at.schedule.app.command.budget.premium;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.UpdateHistPersonCostCalculationCommand;
import nts.uk.ctx.at.schedule.dom.budget.premium.IHistPersonCostCalculationDomainService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class UpdateHistPersonCostCalculationCommandHandler extends CommandHandler<UpdateHistPersonCostCalculationCommand> {
    @Inject
    IHistPersonCostCalculationDomainService service;

    @Override
    protected void handle(CommandHandlerContext<UpdateHistPersonCostCalculationCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        DatePeriod period = new DatePeriod(command.getStartDate(), command.getEndDate());
        service.updateHistPersonCalculation(command.historyId, period);

    }
}
