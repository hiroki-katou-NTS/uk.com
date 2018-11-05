package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveSalIndAmountHisCommandHandler extends CommandHandler<SalIndAmountHisCommand>
{
    
    @Inject
    private SalIndAmountHisRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountHisCommand> context) {
        String historyId = context.getCommand().getYearMonthHistoryItem().get(0).historyId;
        String perValCode = context.getCommand().getPerValCode();
        String empId = context.getCommand().getEmpId();
        String lastHistoryId=context.getCommand().getLastHistoryId();

        repository.remove(historyId, perValCode, empId);
        repository.updateOldHistorty(lastHistoryId,999912);
    }
}
