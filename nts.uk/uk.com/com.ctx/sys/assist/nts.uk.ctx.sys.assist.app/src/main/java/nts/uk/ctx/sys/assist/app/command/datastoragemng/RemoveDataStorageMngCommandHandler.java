package nts.uk.ctx.sys.assist.app.command.datastoragemng;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;

@Stateless
@Transactional
public class RemoveDataStorageMngCommandHandler extends CommandHandler<DataStorageMngCommand>
{
    
    @Inject
    private DataStorageMngRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DataStorageMngCommand> context) {
        String storeProcessingId = context.getCommand().getStoreProcessingId();
        repository.remove(storeProcessingId);
    }
}
