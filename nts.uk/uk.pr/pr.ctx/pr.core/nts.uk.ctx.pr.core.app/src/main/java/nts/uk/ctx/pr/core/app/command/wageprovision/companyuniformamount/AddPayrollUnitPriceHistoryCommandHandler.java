package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryService;


@Stateless
@Transactional
public class AddPayrollUnitPriceHistoryCommandHandler extends CommandHandlerWithResult<PayrollUnitPriceHistoryCommand, String> {

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
