package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteIndEmpSalUnitPriceHistoryCommandHandler extends CommandHandler<DeleteIndEmpSalUnitPriceHistoryCommand> {
    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;


    @Override
    protected void handle(CommandHandlerContext<DeleteIndEmpSalUnitPriceHistoryCommand> commandHandlerContext) {
        DeleteIndEmpSalUnitPriceHistoryCommand command = commandHandlerContext.getCommand();
        employeeSalaryUnitPriceHistoryRepository.deleteHistory(command.getHistoryId());
        employeeSalaryUnitPriceHistoryRepository.updateOldHistory(command.getLastHistoryId(), 999912);
    }
}
