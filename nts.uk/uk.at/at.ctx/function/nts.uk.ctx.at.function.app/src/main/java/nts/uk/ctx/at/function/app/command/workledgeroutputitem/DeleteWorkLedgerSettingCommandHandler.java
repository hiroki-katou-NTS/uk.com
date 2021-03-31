package nts.uk.ctx.at.function.app.command.workledgeroutputitem;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteWorkLedgerSettingCommandHandler extends CommandHandler<DeleteWorkLedgerSettingCommand> {

    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteWorkLedgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String settingId = command.getSettingId();
        workLedgerOutputItemRepo.delete(settingId);

    }
}
