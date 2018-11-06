package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateLifeInsuranceCommandHandler extends CommandHandler<LifeInsuranceCommand>
{
    
    @Inject
    private LifeInsuranceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<LifeInsuranceCommand> context) {
        LifeInsuranceCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        repository.update(new LifeInsurance(cid, command.getLifeInsuranceCode(), command.getLifeInsuranceName()));
    
    }
}
