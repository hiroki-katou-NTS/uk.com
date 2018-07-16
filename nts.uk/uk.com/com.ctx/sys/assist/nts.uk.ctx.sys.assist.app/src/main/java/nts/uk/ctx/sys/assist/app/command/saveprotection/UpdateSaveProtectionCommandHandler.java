package nts.uk.ctx.sys.assist.app.command.saveprotection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.saveprotetion.SaveProtetionRepository;

@Stateless
@Transactional
public class UpdateSaveProtectionCommandHandler extends CommandHandler<SaveProtectionCommand>
{
    
    @Inject
    private SaveProtetionRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SaveProtectionCommand> context) {
        SaveProtectionCommand updateCommand = context.getCommand();
        //repository.update(SaveProtection.createFromJavaType(updateCommand.getCategoryId(), updateCommand.getCorrectClasscification(), updateCommand.getReplaceColumn(), updateCommand.getTableNo()));
    
    }
}
