package nts.uk.ctx.pr.core.app.command.労働保険.労働保険事業所;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveLaborInsuranceOfficeCommandHandler extends CommandHandler<LaborInsuranceOfficeCommand>
{
    
    @Inject
    private LaborInsuranceOfficeRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<LaborInsuranceOfficeCommand> context) {
        repository.remove();
    }
}
