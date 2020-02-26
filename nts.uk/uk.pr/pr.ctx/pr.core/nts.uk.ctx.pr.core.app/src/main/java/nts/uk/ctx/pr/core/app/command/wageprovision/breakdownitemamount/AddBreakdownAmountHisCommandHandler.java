package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class AddBreakdownAmountHisCommandHandler extends CommandHandler<BreakdownAmountHisCommand>
{
    
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
        String historyId = command.getPeriod().get(0).getHistoryId();
        if(historyId == null){
            String historyIdRd = UUID.randomUUID().toString();
            YearMonthHistoryItem period = new YearMonthHistoryItem(
                    historyIdRd,
                    new YearMonthPeriod(
                            new YearMonth(command.getPeriod().get(0).getStartMonth()),
                            new YearMonth(command.getPeriod().get(0).getEndMonth())));
            List lstData = new ArrayList();
            lstData.add(period);
            BreakdownAmountHis breakdownAmountHis = new BreakdownAmountHis(cid, categoryAtr, itemNameCd, employeeId, lstData, salaryBonusAtr);
            repository.add(breakdownAmountHis);
            List<YearMonthHistoryItemCommand> lstHistCommand = command.getPeriod().stream().filter(i -> i.getHistoryId() != null).collect(Collectors.toList());
            List<YearMonthHistoryItem> lstDataHistory = lstHistCommand.stream().map(i -> new YearMonthHistoryItem(i.getHistoryId(), new YearMonthPeriod(new YearMonth(i.getStartMonth()), new YearMonth(i.getEndMonth())))).collect(Collectors.toList());
            BreakdownAmountHis breakdownAmountHisUpdate = new BreakdownAmountHis(cid, categoryAtr, itemNameCd, employeeId, lstDataHistory, salaryBonusAtr);
            repository.update(breakdownAmountHisUpdate);

            List<BreakdownAmountListCommand> lstBreakdownAmountListCommands = command.getBreakdownAmountList();
            List<BreakdownAmountListCommand> dataAmout = lstBreakdownAmountListCommands.stream().filter(i -> i.getAmount() != null).collect(Collectors.toList());
            if(dataAmout.size() == 0){
                return;
            }
            List<BreakdownAmountList> lstBreakdownAmountList = dataAmout.stream().map(i -> new BreakdownAmountList(i.getBreakdownItemCode(), i.getAmount())).collect(Collectors.toList());
            BreakdownAmount data = new BreakdownAmount(historyIdRd, lstBreakdownAmountList);
            breakdownAmountRepository.add(cid,employeeId,categoryAtr,itemNameCd,salaryBonusAtr,data);
        }
        else{
            String historyUpdate = command.getHistoryUpdate();
            List<BreakdownAmountListCommand> lstBreakdownAmountListCommands = command.getBreakdownAmountList();
            List<BreakdownAmountListCommand> dataAmout = lstBreakdownAmountListCommands.stream().filter(i -> i.getAmount() != null).collect(Collectors.toList());
            List<BreakdownAmountListCommand> dataAmoutNull = lstBreakdownAmountListCommands.stream().filter(a -> a.getAmount() == null).collect(Collectors.toList());
            if(dataAmoutNull.size() != 0){
                Optional<BreakdownAmount> breakdownAmountOp = breakdownAmountRepository.getAllBreakdownAmountCode(historyUpdate);
                if(breakdownAmountOp.isPresent()){
                    BreakdownAmount breakdownAmount = breakdownAmountOp.get();
                    List<String> lstBreakCode = breakdownAmount.getBreakdownAmountList().stream().map(i -> i.getBreakdownItemCode().v()).collect(Collectors.toList());
                    List<String> lstCode = dataAmoutNull.stream().map(i -> i.getBreakdownItemCode()).collect(Collectors.toList());
                    List<String> lstCodeUpdate = lstBreakCode.stream().filter(i -> lstCode.contains(i)).collect(Collectors.toList());
                    if(lstCodeUpdate.size() > 0){
                        breakdownAmountRepository.remove(historyUpdate, lstCodeUpdate);
                    }
                }
            }

            if(dataAmout.size() == 0){
                return;
            }
            List<BreakdownAmountList> lstBreakdownAmountList = dataAmout.stream().map(i -> new BreakdownAmountList(i.getBreakdownItemCode(), i.getAmount())).collect(Collectors.toList());
            BreakdownAmount data = new BreakdownAmount(historyUpdate, lstBreakdownAmountList);
            breakdownAmountRepository.update(cid,employeeId,categoryAtr,itemNameCd,salaryBonusAtr,data);
        }

    }
}
