package nts.uk.ctx.pr.core.app.command.wageprovision.organizationinfo.salarycls.salaryclsmaster;

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
public class AddSalaryClsInfoCommandHandler extends CommandHandler<SalaryClsInfoCommand>
{
    
    @Inject
    private SalaryClassificationInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryClsInfoCommand> context) {
        SalaryClsInfoCommand command = context.getCommand();
        repository.add(new SalaryClassificationInformation(AppContexts.user().companyId(), command.getSalaryClassificationCode(), command.getSalaryClassificationName(), command.getMemo()));
    
    }
}
