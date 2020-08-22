package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteBentoCommandHandler extends CommandHandler<DeleteBentoCommand> {


    @Inject
    private BentoMenuRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteBentoCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.max();

        BentoMenu bentoMenu = command.getHistId() == null ?
                bentoMenuRepository.getBentoMenuByEndDate(cid,date) :
                bentoMenuRepository.getBentoMenuByHistId(cid,command.getHistId());
        if (bentoMenu != null){
            Optional<Bento> optionalBento = bentoMenu.getMenu().stream()
                    .filter(x -> x.getFrameNo() == command.getFrameNo())
                    .findFirst();
            optionalBento.ifPresent(bento -> bentoMenu.getMenu().remove(bentoMenu.getMenu().indexOf(bento)));
            bentoMenuRepository.update(bentoMenu);
        }
    }
}
