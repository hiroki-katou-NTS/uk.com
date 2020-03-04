package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveInsuranceTypeCommandHandler extends CommandHandler<InsuranceTypeCommand> {

    @Inject
    private InsuranceTypeRepository repository;

    @Override
    protected void handle(CommandHandlerContext<InsuranceTypeCommand> context) {
        String cid = AppContexts.user().companyId();
        InsuranceTypeCommand command = context.getCommand();
        repository.remove(cid, command.getLifeInsuranceCode(), command.getInsuranceTypeCode());
    }
}
