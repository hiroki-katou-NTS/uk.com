package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class UpdatePayrollUnitPriceHistoryCommandHandler extends CommandHandler<PayrollUnitPriceHistoryCommand>
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
//        repository.update(new PayrollUnitPriceHistory(command.getCode().,command.getCId(),new YearMonthHistoryItem(command.getHisId() , new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth())))));
        YearMonth startYearMonth = new YearMonth(command.getStartYearMonth());
        YearMonth endYearMonth = new YearMonth(command.getEndYearMonth());

        if (command.getIsMode() == MODE_UPDATE ) {
            mPayrollUnitPriceHistoryService.historyCorrectionProcecessing(cId,command.getHisId(),command.getCode(), startYearMonth, endYearMonth);
        } else {
            mPayrollUnitPriceHistoryService.historyDeletionProcessing(command.getHisId(),cId,command.getCode());
        }
    }
}
