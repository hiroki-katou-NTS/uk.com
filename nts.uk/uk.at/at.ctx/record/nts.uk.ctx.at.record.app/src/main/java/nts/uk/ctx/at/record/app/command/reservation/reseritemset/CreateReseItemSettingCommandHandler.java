package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateReseItemSettingCommandHandler extends CommandHandler<CreateReseItemSettingCommand> {

    @Inject
    private BentoMenuRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<CreateReseItemSettingCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();

        Bento bento = new Bento(command.getFrameNo(),
                new BentoName(command.getBenToName()),
                new BentoAmount(command.getAmount1()),
                command.getAmount2() == null ? new BentoAmount(0) : new BentoAmount(command.getAmount2()),
                new BentoReservationUnitName(command.getUnit()),
                EnumAdaptor.valueOf(command.getReceptionTimezoneNo(), ReservationClosingTimeFrame.class),
                command.getWorkLocationCode() != null?
                        Optional.of(new WorkLocationCode(command.getWorkLocationCode())): Optional.empty()
        );
        String cid = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.max();

        BentoMenu bentoMenu = command.getHistId() == null ?
                bentoMenuRepository.getBentoMenuByEndDate(cid,date) :
                bentoMenuRepository.getBentoMenuByHistId(cid,command.getHistId());
        bentoMenu.getMenu().add(bento);

        bentoMenuRepository.update(bentoMenu);
    }
}
