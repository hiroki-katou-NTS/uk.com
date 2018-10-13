package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
        String historyId = context.getCommand().getPeriod().getHistoryID();
        String perValCode = context.getCommand().getPerValCode();
        String empId = context.getCommand().getEmpId();
        repository.remove(historyId, perValCode, empId);
    }
}
