package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmount;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateBreakdownAmountHisCommandHandler extends CommandHandler<BreakdownAmountHisCommand> {

    @Inject
    private BreakdownAmountHisRepository repository;

    @Inject
    private BreakdownAmountRepository breakdownAmountRepository;

    @Override
    protected void handle(CommandHandlerContext<BreakdownAmountHisCommand> context) {
        BreakdownAmountHisCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        int categoryAtr = command.getCategoryAtr();
        String itemNameCd = command.getItemNameCd();
        String employeeId = command.getEmployeeId();
        int salaryBonusAtr = command.getSalaryBonusAtr();
        List<YearMonthHistoryItem> period = command.getPeriod().stream().map(i -> new YearMonthHistoryItem(
                i.getHistoryId(),
                new YearMonthPeriod(
                        new YearMonth(i.getStartMonth()),
                        new YearMonth(i.getEndMonth())
                ))).collect(Collectors.toList());

        BreakdownAmountHis breakdownAmountHis = new BreakdownAmountHis(cid, categoryAtr, itemNameCd, employeeId, period, salaryBonusAtr);
        repository.update(breakdownAmountHis);
        String lastHistoryId = command.getLastHistoryId();
        if (lastHistoryId == null){
            return;
        }

        repository.updateByLastHistoryId(cid, categoryAtr, itemNameCd, employeeId, salaryBonusAtr, lastHistoryId , period.get(0).start().previousMonth().v());
    }
}
