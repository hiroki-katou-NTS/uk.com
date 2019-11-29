package nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveLaborInsuranceOfficeCommandHandler extends CommandHandler<LaborInsuranceOfficeCommand>
{
    
    @Inject
    private LaborInsuranceOfficeRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<LaborInsuranceOfficeCommand> context) {
        repository.remove(context.getCommand().getLaborOfficeCode());
    }
}
