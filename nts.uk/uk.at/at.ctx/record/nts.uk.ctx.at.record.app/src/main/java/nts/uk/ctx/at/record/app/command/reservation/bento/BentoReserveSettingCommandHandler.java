package nts.uk.ctx.at.record.app.command.reservation.bento;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveSettingCommandHandler extends CommandHandler<BentoReserveSettingCommand> {

    @Inject
    private ReservationSettingRepository reservationSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<BentoReserveSettingCommand> commandHandlerContext) {

        BentoReserveSettingCommand command = commandHandlerContext.getCommand();
        if(command.isRegister()) {
        	reservationSettingRepository.add(command.toDomain());
        } else {
        	reservationSettingRepository.update(command.toDomain());
        }
    }
}
