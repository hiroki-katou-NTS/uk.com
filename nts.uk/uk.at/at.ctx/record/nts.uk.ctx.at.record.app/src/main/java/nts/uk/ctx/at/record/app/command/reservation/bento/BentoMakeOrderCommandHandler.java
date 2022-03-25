package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 注文済みにする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoMakeOrderCommandHandler extends CommandHandler<List<BentoMakeOrderCommand>> {

    private final boolean ORDERED = true;

    @Inject
    private ReservationSettingRepository bentoReservationSettingRepository;

    @Inject
    private BentoReservationRepository bentoReservationRepository;

    @Override
    protected void handle(CommandHandlerContext<List<BentoMakeOrderCommand>> context) {
        // OrderDeadline orderDeadlineTemp = OrderDeadline.DURING_CLOSING_PERIOD;
        Optional<BentoReservation> temp;
        List<BentoMakeOrderCommand> commands = context.getCommand();
        Optional<ReservationSetting> bentoReservationSetting = bentoReservationSettingRepository.findByCId(AppContexts.user().companyId());

        if(bentoReservationSetting.isPresent())
           // orderDeadlineTemp = bentoReservationSetting.get().getCorrectionContent().getOrderDeadline();

        // if(orderDeadlineTemp == OrderDeadline.ALWAYS){
            for(BentoMakeOrderCommand command :commands ){
                temp = bentoReservationRepository.find(command.getReservationRegisterInfo(), command.getReservationDate());
                if(temp.isPresent()){
                    BentoReservation bentoReservation = temp.get();
                    bentoReservation.setOrdered(ORDERED);
                    AtomTask persist = AtomTask.of(() -> {
                        bentoReservationRepository.update(bentoReservation);
                    });
                    transaction.execute(() -> {
                        persist.run();
                    });
                }
            }
        // }
    }
}
