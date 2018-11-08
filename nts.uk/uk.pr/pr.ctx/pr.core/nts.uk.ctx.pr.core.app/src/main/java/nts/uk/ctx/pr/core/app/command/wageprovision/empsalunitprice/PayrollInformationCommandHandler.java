package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
public class PayrollInformationCommandHandler extends CommandHandler<PayrollInformationCommands> {

    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Override
    @Transactional
    protected void handle(CommandHandlerContext<PayrollInformationCommands> commandHandlerContext) {
        PayrollInformationCommands commands = commandHandlerContext.getCommand();
        for (PayrollInformationCommand item : commands.getPayrollInformationCommands()) {
            this.employeeSalaryUnitPriceHistoryRepository.updateAllHistory(item.getHistoryID(), item.getAmountOfMoney());
        }
    }
}
