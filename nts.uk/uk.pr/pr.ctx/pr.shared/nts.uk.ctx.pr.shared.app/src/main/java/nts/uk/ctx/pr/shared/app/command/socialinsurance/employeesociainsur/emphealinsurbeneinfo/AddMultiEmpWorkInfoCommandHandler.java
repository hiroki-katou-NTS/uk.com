package .app.command.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class AddMultiEmpWorkInfoCommandHandler extends CommandHandler<MultiEmpWorkInfoCommand>
{
    
    @Inject
    private MultiEmpWorkInfoRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<MultiEmpWorkInfoCommand> context) {
        MultiEmpWorkInfoCommand command = context.getCommand();
        repository.add(new MultiEmpWorkInfo(command.getEmpId(), command.getIsMoreEmp()));
    
    }
}
