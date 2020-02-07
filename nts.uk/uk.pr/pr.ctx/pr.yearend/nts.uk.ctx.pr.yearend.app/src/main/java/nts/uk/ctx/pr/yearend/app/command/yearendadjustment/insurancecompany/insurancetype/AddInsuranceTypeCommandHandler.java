package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceType;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddInsuranceTypeCommandHandler extends CommandHandler<InsuranceTypeCommand> {

    @Inject
    private InsuranceTypeRepository repository;

    @Override
    protected void handle(CommandHandlerContext<InsuranceTypeCommand> context) {
        InsuranceTypeCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        InsuranceType insuranceType = new InsuranceType(cid, command.getLifeInsuranceCode(), command.getInsuranceTypeCode(), command.getInsuranceTypeName(), command.getAtrOfInsuranceType());
        if(repository.getInsuranceTypeById(cid, command.getLifeInsuranceCode(), command.getInsuranceTypeCode()).isPresent()) {
            throw new BusinessException("Msg_3");
        }
        repository.add(insuranceType);
    }
}
