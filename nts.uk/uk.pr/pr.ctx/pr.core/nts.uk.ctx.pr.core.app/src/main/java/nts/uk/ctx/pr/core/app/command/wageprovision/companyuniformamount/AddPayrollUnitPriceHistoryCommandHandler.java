package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class AddPayrollUnitPriceHistoryCommandHandler extends CommandHandler<PayrollUnitPriceHistoryCommand>
{
    
    @Inject
    private PayrollUnitPriceHistoryRepository repository;

    @Inject
    private PayrollUnitPriceHistoryService mPayrollUnitPriceHistoryService;

    public static final int MODE_DELETE = 0;

    public static final int MODE_UPDATE = 1;

    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceHistoryCommand> context) {
        String cId = AppContexts.user().companyId();
        PayrollUnitPriceHistoryCommand command = context.getCommand();
//        repository.add(new PayrollUnitPriceHistory(command.getCode(),command.getCId(),new YearMonthHistoryItem(command.getHisId() , new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth())))));
        YearMonth startYearMonth = new YearMonth(command.getStartYearMonth());
        YearMonth endYearMonth = new YearMonth(command.getEndYearMonth());

        if (command.getIsMode() == MODE_DELETE ) {
            mPayrollUnitPriceHistoryService.updatePayrollUnitPriceHistory(command.getHisId(),command.getCode(), startYearMonth, endYearMonth);
        } else {
            mPayrollUnitPriceHistoryService.deletePayrollUnitPriceHis(command.getHisId(),command.getCode());
        }
    }
}
