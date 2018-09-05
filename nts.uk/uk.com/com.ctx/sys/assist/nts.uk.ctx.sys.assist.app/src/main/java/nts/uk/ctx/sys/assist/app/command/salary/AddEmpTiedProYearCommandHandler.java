package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.EmpTiedProYearRepository;
import nts.uk.ctx.sys.assist.dom.salary.EmpTiedProYear;

@Stateless
@Transactional
public class AddEmpTiedProYearCommandHandler extends CommandHandler<EmpTiedProYearCommand>
{
    
    @Inject
    private EmpTiedProYearRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpTiedProYearCommand> context) {
        EmpTiedProYearCommand addCommand = context.getCommand();
        repository.add(new EmpTiedProYear(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getEmploymentCode()));
    
    }
}
