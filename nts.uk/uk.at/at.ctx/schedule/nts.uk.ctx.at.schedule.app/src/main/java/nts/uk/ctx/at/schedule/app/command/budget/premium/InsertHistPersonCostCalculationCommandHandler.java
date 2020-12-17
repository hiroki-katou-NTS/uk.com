package nts.uk.ctx.at.schedule.app.command.budget.premium;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.InsertHistPersonCostCalculationCommand;
import nts.uk.ctx.at.schedule.dom.budget.premium.IHistPersonCostCalculationDomainService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author  chinh.hm
 */
@Stateless
@Transactional
public class InsertHistPersonCostCalculationCommandHandler extends CommandHandler<InsertHistPersonCostCalculationCommand>{

    @Inject
    IHistPersonCostCalculationDomainService service;
    @Override
    protected void handle(CommandHandlerContext<InsertHistPersonCostCalculationCommand> commandHandlerContext) {
            val command = commandHandlerContext.getCommand();
            service.createHistPersonCostCalculation(command.getDate());
    }
}
