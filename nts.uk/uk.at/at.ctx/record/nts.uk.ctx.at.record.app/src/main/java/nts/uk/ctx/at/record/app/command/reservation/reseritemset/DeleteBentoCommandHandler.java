package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteBentoCommandHandler extends CommandHandler<DeleteBentoCommand> {


    @Inject
    private BentoMenuHistRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteBentoCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();

        BentoMenuHistory bentoMenu = bentoMenuRepository.findByHistoryID(command.getHistId()).get();
        if (bentoMenu != null){
            Optional<Bento> optionalBento = bentoMenu.getMenu().stream()
                    .filter(x -> x.getFrameNo() == command.getFrameNo())
                    .findFirst();
            if (bentoMenu.getMenu().size() <= 1){
                throw new BusinessException("Msg_1849");
            }
            optionalBento.ifPresent(bento -> bentoMenu.getMenu().remove(bentoMenu.getMenu().indexOf(bento)));
            bentoMenuRepository.update(bentoMenu);
        }
    }
}
