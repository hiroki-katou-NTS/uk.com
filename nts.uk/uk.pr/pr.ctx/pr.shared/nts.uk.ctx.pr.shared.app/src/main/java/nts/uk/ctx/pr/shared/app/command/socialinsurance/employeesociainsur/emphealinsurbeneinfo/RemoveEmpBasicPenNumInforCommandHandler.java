package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveEmpBasicPenNumInforCommandHandler extends CommandHandler<EmpBasicPenNumInforCommand>
{
    
    @Inject
    private EmpBasicPenNumInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpBasicPenNumInforCommand> context) {
        String employeeId = context.getCommand().getEmployeeId();
        repository.remove(employeeId);
    }
}
