package nts.uk.ctx.pr.core.app.command.wageprovision.salaryindividualamountname;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddSalIndAmountNameCommandHandler extends CommandHandler<SalIndAmountNameCommand>
{
    
    @Inject
    private SalIndAmountNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountNameCommand> context) {
        SalIndAmountNameCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        if(repository.getSalIndAmountNameById(cid, command.getIndividualPriceCode(), command.getCateIndicator()).isPresent()){
            throw new BusinessException("Msg_3");
        }
        SalIndAmountName salIndAmountName = new SalIndAmountName(cid, command.getIndividualPriceCode(), command.getCateIndicator(), command.getIndividualPriceName());
        repository.add(salIndAmountName);
    
    }
}
