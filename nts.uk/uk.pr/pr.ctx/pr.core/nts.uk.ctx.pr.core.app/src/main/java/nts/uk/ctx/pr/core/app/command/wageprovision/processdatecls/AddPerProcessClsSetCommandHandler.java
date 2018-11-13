package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSetRepository;

@Stateless
@Transactional
public class AddPerProcessClsSetCommandHandler extends CommandHandler<PerProcessClsSetCommand>
{
    
    @Inject
    private PerProcessClsSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PerProcessClsSetCommand> context) {
        PerProcessClsSetCommand command = context.getCommand();
        repository.add(new PerProcessClsSet(command.getCid(), command.getProcessCateNo(), command.getUid()));
    
    }
}
