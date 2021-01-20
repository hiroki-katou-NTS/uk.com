package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteOutputSettingCommandHandler extends CommandHandler<DeleteOutputSettingCommand> {

    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteOutputSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String settingId = command.getSettingId();
        ofArbitraryRepo.delete(settingId);

    }
}
