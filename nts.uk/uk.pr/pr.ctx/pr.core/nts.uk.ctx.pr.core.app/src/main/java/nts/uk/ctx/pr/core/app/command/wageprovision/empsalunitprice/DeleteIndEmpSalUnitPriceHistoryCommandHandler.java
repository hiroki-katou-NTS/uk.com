package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteIndEmpSalUnitPriceHistoryCommandHandler extends CommandHandler<String> {
    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<String> commandHandlerContext) {
        String historyId = commandHandlerContext.getCommand();
        employeeSalaryUnitPriceHistoryRepository.deleteHistory(historyId);
    }
}
