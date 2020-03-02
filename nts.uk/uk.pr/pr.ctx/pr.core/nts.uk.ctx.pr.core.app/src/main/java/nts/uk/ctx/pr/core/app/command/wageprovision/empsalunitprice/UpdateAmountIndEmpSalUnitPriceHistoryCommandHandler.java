package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.PayrollInformation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateAmountIndEmpSalUnitPriceHistoryCommandHandler extends CommandHandler<UpdateAmountIndEmpSalUnitPriceHistoryCommand> {

    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateAmountIndEmpSalUnitPriceHistoryCommand> commandHandlerContext) {
        UpdateAmountIndEmpSalUnitPriceHistoryCommand command = commandHandlerContext.getCommand();
        employeeSalaryUnitPriceHistoryRepository.updateAmount(new PayrollInformation(command.getHistoryId(), command.getAmountOfMoney()));
    }
}
