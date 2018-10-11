package nts.uk.ctx.pr.core.app.command.wageprovision.salaryindividualamountname;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;

@Stateless
@Transactional
public class AddSalIndAmountNameCommandHandler extends CommandHandler<SalIndAmountNameCommand>
{
    
    @Inject
    private SalIndAmountNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountNameCommand> context) {
        SalIndAmountNameCommand command = context.getCommand();
        repository.add(new SalIndAmountName(command.getCId(), command.getIndividualPriceCode(), command.getCateIndicator(), command.getIndividualPriceName()));
    
    }
}
