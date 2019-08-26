package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddMultiEmpWorkInfoCommandHandler extends CommandHandler<MultiEmpWorkInfoCommand>
{
    
    @Inject
    private MultiEmpWorkInfoRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<MultiEmpWorkInfoCommand> context) {
        MultiEmpWorkInfoCommand command = context.getCommand();
        repository.add(new MultiEmpWorkInfo(command.getEmployeeId(), command.getIsMoreEmp()));
    
    }
}
