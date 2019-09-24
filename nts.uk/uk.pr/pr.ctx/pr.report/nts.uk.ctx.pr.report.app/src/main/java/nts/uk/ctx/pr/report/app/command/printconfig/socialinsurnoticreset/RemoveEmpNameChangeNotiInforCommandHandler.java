package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveEmpNameChangeNotiInforCommandHandler extends CommandHandler<EmpNameChangeNotiInforCommand>
{
    
    @Inject
    private EmpNameChangeNotiInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpNameChangeNotiInforCommand> context) {
        String employeeId = context.getCommand().getEmployeeId();
        String cid = AppContexts.user().companyId();
        repository.remove(employeeId, cid);
    }
}
