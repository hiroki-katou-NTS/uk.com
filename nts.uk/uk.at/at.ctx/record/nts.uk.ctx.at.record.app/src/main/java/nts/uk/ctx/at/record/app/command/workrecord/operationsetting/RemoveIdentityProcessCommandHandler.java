package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;

@Stateless
@Transactional
public class RemoveIdentityProcessCommandHandler extends CommandHandler<IdentityProcessCommand>
{
    
    @Inject
    private IdentityProcessRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<IdentityProcessCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
