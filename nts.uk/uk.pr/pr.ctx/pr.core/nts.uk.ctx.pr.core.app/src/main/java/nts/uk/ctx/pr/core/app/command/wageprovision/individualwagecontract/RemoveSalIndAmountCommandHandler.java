package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveSalIndAmountCommandHandler extends CommandHandler<SalIndAmountCommand>
{
    
    @Inject
    private SalIndAmountRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountCommand> context) {
        String historyId = context.getCommand().getHistoryId();
        repository.remove(historyId);
    }
}
