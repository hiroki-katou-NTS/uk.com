package nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveSalaryClassificationInformationCommandHandler extends CommandHandler<String>
{
    
    @Inject
    private SalaryClassificationInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<String> context) {
        repository.remove(AppContexts.user().companyId(), context.getCommand());
    }
}
