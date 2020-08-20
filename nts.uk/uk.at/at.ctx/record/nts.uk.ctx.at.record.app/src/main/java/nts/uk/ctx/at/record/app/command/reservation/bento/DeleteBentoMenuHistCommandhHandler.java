package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteBentoMenuHistCommandhHandler extends CommandHandler<DeleteBentoMenuHistCommand> {
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteBentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        val listOld = bentoMenuHistoryRepository.findByCompanyId(cid);

        if (!listOld.isPresent()){
            throw new BusinessException("invalid BentoMenuHistory!");
        }
        // Get item delete
        Optional<DateHistoryItem> optionalHisItem = listOld.get().getHistoryItems().stream()
                .filter(x -> x.identifier().equals(command.historyId)).findFirst();
        if (!optionalHisItem.isPresent()) {

            throw new BusinessException("invalid BentoMenuHistory!");
        }
        // Remove history in list
        listOld.get().remove(optionalHisItem.get());

        // Delete history
        bentoMenuHistoryRepository.delete(cid,command.getHistoryId());

        // Update before history
        bentoMenuHistoryRepository.update(listOld.get());
    }
}
