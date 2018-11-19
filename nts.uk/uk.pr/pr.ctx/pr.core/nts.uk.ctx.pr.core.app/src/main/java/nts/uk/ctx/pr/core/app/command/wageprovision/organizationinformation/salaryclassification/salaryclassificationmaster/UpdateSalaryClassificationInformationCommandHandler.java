package nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateSalaryClassificationInformationCommandHandler extends CommandHandler<SalaryClassificationInformationCommand>
{
    
    @Inject
    private SalaryClassificationInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryClassificationInformationCommand> context) {
        SalaryClassificationInformationCommand command = context.getCommand();
        repository.update(new SalaryClassificationInformation(AppContexts.user().companyId(), command.getSalaryClassificationCode(), command.getSalaryClassificationName(), command.getMemo()));
    
    }
}
