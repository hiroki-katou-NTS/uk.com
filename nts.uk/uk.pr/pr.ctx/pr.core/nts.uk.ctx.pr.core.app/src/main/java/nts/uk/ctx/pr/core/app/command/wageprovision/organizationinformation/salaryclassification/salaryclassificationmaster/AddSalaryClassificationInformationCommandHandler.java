package nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddSalaryClassificationInformationCommandHandler extends CommandHandler<SalaryClassificationInformationCommand>
{
    
    @Inject
    private SalaryClassificationInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryClassificationInformationCommand> context) {
        SalaryClassificationInformationCommand command = context.getCommand();
        repository.add(new SalaryClassificationInformation(AppContexts.user().companyId(), command.getSalaryClassificationCode(), command.getSalaryClassificationName(), command.getMemo()));
    
    }
}
