package nts.uk.ctx.pr.core.app.command.労働保険.労働保険事業所;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateLaborInsuranceOfficeCommandHandler extends CommandHandler<LaborInsuranceOfficeCommand>
{
    
    @Inject
    private LaborInsuranceOfficeRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<LaborInsuranceOfficeCommand> context) {
        LaborInsuranceOfficeCommand command = context.getCommand();
        repository.update(new LaborInsuranceOffice(command.getCompanyId(), command.getOfficeCode(), command.getOfficeName(), command.getNotes(), command.getRepresentativePosition(), command.get(), command.get(), command.get(), command.get(), command.get(), command.getPhoneNumber(), command.getPostalCode(), command.get(), command.get(), command.get(), command.get(), command.get()));
    
    }
}
