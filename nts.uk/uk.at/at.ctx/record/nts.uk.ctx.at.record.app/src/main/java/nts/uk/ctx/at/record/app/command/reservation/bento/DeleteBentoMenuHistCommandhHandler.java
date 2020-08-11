package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteBentoMenuHistCommandhHandler extends CommandHandler<DeleteBentoMenuHistCommand> {
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteBentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        bentoMenuHistoryRepository.delete(cid,command.getHistotyId());
    }
}
