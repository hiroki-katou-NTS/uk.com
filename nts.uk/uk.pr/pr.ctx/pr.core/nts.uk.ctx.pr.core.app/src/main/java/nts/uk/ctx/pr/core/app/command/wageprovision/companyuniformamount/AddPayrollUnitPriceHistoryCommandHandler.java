package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;


@Stateless
@Transactional
public class AddPayrollUnitPriceHistoryCommandHandler extends CommandHandler<PayrollUnitPriceHistoryCommand> {

    @Inject
    private PayrollUnitPriceHistoryService payrollUnitPriceHistoryService;


    @Override
    protected String handle(CommandHandlerContext<PayrollUnitPriceHistoryCommand> context) {
        PayrollUnitPriceHistoryCommand command = context.getCommand();
        YearMonth startYearMonth = new YearMonth(command.getStartYearMonth());
        YearMonth endYearMonth = new YearMonth(command.getEndYearMonth());
        return payrollUnitPriceHistoryService.addPayrollUnitPriceHistory(command.getCode(), startYearMonth, endYearMonth);
    }
}
