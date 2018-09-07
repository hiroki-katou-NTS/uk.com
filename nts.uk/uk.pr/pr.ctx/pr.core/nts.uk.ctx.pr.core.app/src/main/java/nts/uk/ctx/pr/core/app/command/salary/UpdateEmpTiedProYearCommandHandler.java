package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.salary.EmpTiedProYearRepository;

@Stateless
@Transactional
public class UpdateEmpTiedProYearCommandHandler extends CommandHandler<EmpTiedProYearCommand>
{
    
    @Inject
    private EmpTiedProYearRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpTiedProYearCommand> context) {
        EmpTiedProYearCommand updateCommand = context.getCommand();
        repository.update(new EmpTiedProYear(updateCommand.getCid(), updateCommand.getProcessCateNo(), updateCommand.getEmploymentCode()));
    
    }
}
