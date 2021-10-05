package nts.uk.ctx.exio.app.command.exo.externaloutput;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateExOutputCtgAuthCommandHandler  extends CommandHandler<List<DuplicateExOutputCtgAuthCommand>> {
    @Override
    protected void handle(CommandHandlerContext<List<DuplicateExOutputCtgAuthCommand>> commandHandlerContext) {

    }
}
