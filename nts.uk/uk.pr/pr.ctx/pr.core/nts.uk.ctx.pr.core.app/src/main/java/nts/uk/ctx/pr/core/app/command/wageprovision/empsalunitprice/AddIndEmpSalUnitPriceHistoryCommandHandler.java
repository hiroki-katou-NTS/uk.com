package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.PayrollInformation;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;

@Stateless
@Transactional
public class AddIndEmpSalUnitPriceHistoryCommandHandler extends CommandHandler<AddIndEmpSalUnitPriceHistoryCommand> {
    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<AddIndEmpSalUnitPriceHistoryCommand> commandHandlerContext) {
        AddIndEmpSalUnitPriceHistoryCommand command = commandHandlerContext.getCommand();
        employeeSalaryUnitPriceHistoryRepository.addHistory(
                new EmployeeSalaryUnitPriceHistory(
                        command.getPersonalUnitPriceCode(),
                        command.getEmployeeId(),
                        Collections.singletonList(new YearMonthHistoryItem(command.getHistoryId(), new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth()))))),
                new PayrollInformation(command.getHistoryId(), command.getAmountOfMoney()));
        if(command.getOldHistoryId() != null && command.getNewEndYearMonth() != 0) {
            employeeSalaryUnitPriceHistoryRepository.updateOldHistory(command.getOldHistoryId(), command.getNewEndYearMonth());
        }
    }
}
