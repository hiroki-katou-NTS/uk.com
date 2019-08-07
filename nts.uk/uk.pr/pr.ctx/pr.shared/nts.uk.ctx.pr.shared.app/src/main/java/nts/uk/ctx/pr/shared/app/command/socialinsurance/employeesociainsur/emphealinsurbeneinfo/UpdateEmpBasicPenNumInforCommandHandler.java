package .app.command.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateEmpBasicPenNumInforCommandHandler extends CommandHandler<EmpBasicPenNumInforCommand>
{
    
    @Inject
    private EmpBasicPenNumInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpBasicPenNumInforCommand> context) {
        EmpBasicPenNumInforCommand command = context.getCommand();
        repository.update(new EmpBasicPenNumInfor(command.getEmployeeId(), command.getBasicPenNumber()));
    
    }
}
