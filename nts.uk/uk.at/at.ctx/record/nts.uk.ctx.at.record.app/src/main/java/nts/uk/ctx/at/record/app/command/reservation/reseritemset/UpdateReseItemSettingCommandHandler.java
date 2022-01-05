package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateReseItemSettingCommandHandler extends CommandHandler<UpdateReseItemSettingCommand> {

    @Inject
    private BentoMenuHistRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateReseItemSettingCommand> commandHandlerContext) {

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

        BentoMenuHistory bentoMenu = bentoMenuRepository.findByHistoryID(command.getHistId()).get();

        Optional<Bento> optionalBento = bentoMenu.getMenu() .stream().filter(x -> x.getFrameNo() == bento.getFrameNo()) .findFirst();
        optionalBento.ifPresent(bento1 -> bentoMenu.getMenu().set(bentoMenu.getMenu().indexOf(bento1), bento));

        bentoMenuRepository.update(bentoMenu);
    }

}
