package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 従業員の弁当予約を強制修正する
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ForceUpdateBentoReserveCommandHandler extends CommandHandler<ForceUpdateBentoReserveCommand> {
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    @Override
    protected void handle(CommandHandlerContext<ForceUpdateBentoReserveCommand> context) {
        String companyId = AppContexts.user().companyId();
        String employeeId = AppContexts.user().employeeId();
        ForceUpdateBentoReserveCommand command = context.getCommand();

        ReservationDate reservationDate = new ReservationDate(command.getDate(), EnumAdaptor.valueOf(command.getClosingTimeFrame(), ReservationClosingTimeFrame.class));
        List<BentoReservationInfoTemp> bentoReservationInfos = new ArrayList<>();
        GeneralDateTime dateTime = GeneralDateTime.now();
        Optional<WorkLocationCode> workLocationCode = Optional.empty();
        if (command.isNew()) {
            RequireForceAddImpl require = new RequireForceAddImpl(bentoReservationRepository);
            AtomTask persist = ForceAddBentoReservationService.forceAdd(require, bentoReservationInfos, reservationDate,
                    dateTime, workLocationCode);
            transaction.execute(persist::run);
        } else {
            RequireForceUpdateImpl require = new RequireForceUpdateImpl(bentoReservationRepository);
            AtomTask persist = ForceUpdateBentoReservationService.forceUpdate(require, reservationDate, bentoReservationInfos);
            transaction.execute(persist::run);
        }

    }

    @AllArgsConstructor
    private class RequireForceAddImpl implements ForceAddBentoReservationService.Require {
        private BentoReservationRepository bentoReservationRepository;

        @Override
        public void add(BentoReservation bentoReservation) {
            bentoReservationRepository.add(bentoReservation);
        }
    }

    @AllArgsConstructor
    private class RequireForceUpdateImpl implements ForceUpdateBentoReservationService.Require {
        private BentoReservationRepository bentoReservationRepository;

        @Override
        public BentoReservation get(ReservationRegisterInfo registerInfo, ReservationDate reservationDate) {
            List<ReservationRegisterInfo> inforLst = new ArrayList<>();
            inforLst.add(registerInfo);
            List<BentoReservation> bentoReservations = bentoReservationRepository.getReservationInformation(inforLst, reservationDate);
            if (bentoReservations.isEmpty()) return null;
            return bentoReservations.get(0);
        }

        @Override
        public void update(BentoReservation bentoReservation) {
            bentoReservationRepository.update(bentoReservation);
        }
    }
}
