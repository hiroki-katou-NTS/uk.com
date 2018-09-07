package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.ProcessInformation;
import nts.uk.ctx.pr.core.dom.salary.ProcessInformationRepository;

@Stateless
@Transactional
public class UpdateProcessInformationCommandHandler extends CommandHandler<ProcessInformationCommand>
{
    
    @Inject
    private ProcessInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ProcessInformationCommand> context) {
        ProcessInformationCommand updateCommand = context.getCommand();
        repository.update(new ProcessInformation(updateCommand.getCid(), updateCommand.getProcessCateNo(), updateCommand.getDeprecatCate(), updateCommand.getProcessDivisionName()));
    
    }
}
