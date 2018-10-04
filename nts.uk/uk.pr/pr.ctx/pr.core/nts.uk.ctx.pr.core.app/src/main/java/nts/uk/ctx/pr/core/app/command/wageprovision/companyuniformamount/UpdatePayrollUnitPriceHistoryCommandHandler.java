package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class UpdatePayrollUnitPriceHistoryCommandHandler extends CommandHandler<PayrollUnitPriceHistoryCommand>
{
    
    @Inject
    private PayrollUnitPriceHistoryRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceHistoryCommand> context) {
        PayrollUnitPriceHistoryCommand command = context.getCommand();
//        repository.update(new PayrollUnitPriceHistory(command.getCode().,command.getCId(),new YearMonthHistoryItem(command.getHisId() , new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth())))));
    }
}
