package nts.uk.ctx.pr.core.app.command.wageprovision.salaryindividualamountname;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveSalIndAmountNameCommandHandler extends CommandHandler<SalIndAmountNameCommand>
{
    
    @Inject
    private SalIndAmountNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountNameCommand> context) {
        String cid = AppContexts.user().companyId();
        String individualPriceCode = context.getCommand().getIndividualPriceCode();
        int cateIndicator=context.getCommand().getCateIndicator();
        repository.remove(cid, individualPriceCode,cateIndicator);
    }
}
