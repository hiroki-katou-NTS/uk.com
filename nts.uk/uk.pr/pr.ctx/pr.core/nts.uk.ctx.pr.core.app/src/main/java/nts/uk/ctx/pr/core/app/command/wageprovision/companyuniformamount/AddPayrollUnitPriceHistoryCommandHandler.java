package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryService;
import nts.uk.shr.com.context.AppContexts;

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

    }
}
