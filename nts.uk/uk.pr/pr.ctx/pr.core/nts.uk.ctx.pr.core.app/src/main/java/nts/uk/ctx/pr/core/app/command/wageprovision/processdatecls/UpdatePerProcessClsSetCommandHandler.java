package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdatePerProcessClsSetCommandHandler extends CommandHandler<PerProcessClsSetCommand>
{
    
    @Inject
    private PerProcessClsSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PerProcessClsSetCommand> context) {
        PerProcessClsSetCommand command = context.getCommand();
        repository.update(new PerProcessClsSet(command.getCid(), command.getProcessCateNo(), command.getUid()));
    
    }
}
