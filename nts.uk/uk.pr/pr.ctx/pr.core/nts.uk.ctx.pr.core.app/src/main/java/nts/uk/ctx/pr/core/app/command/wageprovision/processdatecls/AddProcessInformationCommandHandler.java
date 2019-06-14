package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;

@Stateless
@Transactional
public class AddProcessInformationCommandHandler extends CommandHandler<ProcessInformationCommand>
{
    
    @Inject
    private ProcessInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ProcessInformationCommand> context) {
        ProcessInformationCommand addCommand = context.getCommand();
        repository.add(new ProcessInformation(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getDeprecatCate(), addCommand.getProcessCls()));
    
    }
}
