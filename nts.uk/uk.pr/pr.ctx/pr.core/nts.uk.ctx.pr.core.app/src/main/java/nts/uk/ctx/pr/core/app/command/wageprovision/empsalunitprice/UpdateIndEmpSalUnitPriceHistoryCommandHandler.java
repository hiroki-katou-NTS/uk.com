package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.PayrollInformation;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;

@Stateless
@Transactional
public class UpdateIndEmpSalUnitPriceHistoryCommandHandler extends CommandHandler<IndEmpSalUnitPriceHistoryCommand> {
    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<IndEmpSalUnitPriceHistoryCommand> commandHandlerContext) {
        IndEmpSalUnitPriceHistoryCommand command = commandHandlerContext.getCommand();
        employeeSalaryUnitPriceHistoryRepository.updateHistory(
                new EmployeeSalaryUnitPriceHistory(
                        command.getPersonalUnitPriceCode(),
                        command.getEmployeeId(),
                        Collections.singletonList(new YearMonthHistoryItem(command.getHistoryId(), new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth())))))
        );
    }
}
