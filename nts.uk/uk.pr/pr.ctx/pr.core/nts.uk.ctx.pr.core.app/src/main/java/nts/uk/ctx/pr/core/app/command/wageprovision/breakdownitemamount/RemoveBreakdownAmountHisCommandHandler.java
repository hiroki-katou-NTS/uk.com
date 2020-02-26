package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.BreakdownAmountHisDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.BreakdownAmountHisFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHisRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class RemoveBreakdownAmountHisCommandHandler extends CommandHandler<BreakdownAmountHisCommand> {

    @Inject
    private BreakdownAmountHisRepository repository;

    @Inject
    private BreakdownAmountRepository breakdownAmountRepository;

    @Inject
    BreakdownAmountHisFinder finder;

    @Override
    protected void handle(CommandHandlerContext<BreakdownAmountHisCommand> context) {
        BreakdownAmountHisCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        int categoryAtr = command.getCategoryAtr();
        String itemNameCd = command.getItemNameCd();
        String employeeId = command.getEmployeeId();
        int salaryBonusAtr = command.getSalaryBonusAtr();
        String historyId = command.getPeriod().get(0).getHistoryId();
        repository.remove(cid, categoryAtr, itemNameCd, employeeId, salaryBonusAtr, historyId);
        breakdownAmountRepository.removeByHistoryId(historyId);
        String lastHistoryId = command.getLastHistoryId();
        if (lastHistoryId == null){
            return;
        }
        repository.updateByHistoryId(cid, categoryAtr, itemNameCd, employeeId, salaryBonusAtr, lastHistoryId);
    }
}
