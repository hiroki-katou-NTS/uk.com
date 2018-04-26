package nts.uk.ctx.sys.assist.app.command.saveProtection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.saveProtection.SaveProtectionRepository;

@Stateless
@Transactional
public class RemoveSaveProtectionCommandHandler extends CommandHandler<SaveProtectionCommand>
{
    
    @Inject
    private SaveProtectionRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SaveProtectionCommand> context) {
        repository.remove();
    }
}
