package nts.uk.ctx.pr.core.app.command.wageprovision.salaryindividualamountname;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveSalIndAmountNameCommandHandler extends CommandHandler<SalIndAmountNameCommand>
{
    
    @Inject
    private SalIndAmountNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountNameCommand> context) {
        String cid = context.getCommand().getCId();
        String individualPriceCode = context.getCommand().getIndividualPriceCode();
        repository.remove(cid, individualPriceCode);
    }
}
