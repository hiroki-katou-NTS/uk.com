package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveLifeInsuranceCommandHandler extends CommandHandler<LifeInsuranceCommand>
{
    
    @Inject
    private LifeInsuranceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<LifeInsuranceCommand> context) {
        String cid = AppContexts.user().companyId();
        String lifeInsuranceCode = context.getCommand().getLifeInsuranceCode();
        repository.remove(cid, lifeInsuranceCode);
        repository.removeLifeInsurance(cid, lifeInsuranceCode);
    }
}
