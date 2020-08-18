package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.RegisterReservationLunchService;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveSettingCommandHandler extends CommandHandler<BentoReserveSettingCommand> {

    @Inject
    private BentoReservationSettingRepository reservationSettingRepository;

    @Inject
    private BentoMenuRepository bentoMenuRepository;

    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    @Override
    protected void handle(CommandHandlerContext<BentoReserveSettingCommand> commandHandlerContext) {

        BentoReserveSettingCommand command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(reservationSettingRepository,bentoMenuRepository,bentoMenuHistoryRepository);

        Achievements achievements =  new Achievements(
                new ReferenceTime(command.getReferenceTime()),
                AchievementMethod.valueOf(command.getDailyResults()),
                AchievementMethod.valueOf(command.getMonthlyResults())
        );
        CorrectionContent correctionContent = new CorrectionContent(
                ContentChangeDeadline.valueOf(command.getContentChangeDeadline()),
                ContentChangeDeadlineDay.valueOf(command.getContentChangeDeadlineDay()),
                OrderedData.valueOf(command.getOrderedData()),
                OrderDeadline.valueOf(command.getOrderDeadline())
        );
        ReservationClosingTime closingTime1 = new ReservationClosingTime(new BentoReservationTimeName(command.getName1()),
                new BentoReservationTime(command.getEnd1()),
                Optional.of(new BentoReservationTime(command.getStart1())));

        Optional<ReservationClosingTime> closingTime2 = Optional.of(new ReservationClosingTime(
                new BentoReservationTimeName(command.getName2()),
                new BentoReservationTime(command.getEnd2()),
                Optional.of(new BentoReservationTime(command.getStart2()))));

        BentoReservationClosingTime bentoReservationClosingTime = new BentoReservationClosingTime(closingTime1,closingTime2);
        OperationDistinction operationDistinction = OperationDistinction.valueOf(command.getOperationDistinction());
        AtomTask persist = RegisterReservationLunchService.register(
                require, operationDistinction,achievements,correctionContent,bentoReservationClosingTime);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    private class RequireImpl implements RegisterReservationLunchService.Require{

        private BentoReservationSettingRepository reservationSettingRepository;
        private BentoMenuRepository bentoMenuRepository;
        private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

        @Override
        public BentoReservationSetting getReservationSettings(String cid) {
            return reservationSettingRepository.findByCId(cid).get();
        }

        @Override
        public BentoMenu getBentoMenu(String cid, GeneralDate date) {
            return bentoMenuRepository.getBentoMenuByEndDate(cid,date);
        }

        @Override
        public void registerBentoMenu(String historyID, BentoReservationClosingTime bentoReservationClosingTime) {

            String companyId = AppContexts.user().companyId();
            if (historyID == null){
                val hist = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(),GeneralDate.max()));

                bentoMenuHistoryRepository.add(new BentoMenuHistory(companyId, new ArrayList<>(Arrays.asList(hist))));

                bentoMenuRepository.add(new BentoMenu(hist.identifier() , Collections.EMPTY_LIST,bentoReservationClosingTime));
            }else {
                //get bentomenu
                BentoMenu bentoMenu = bentoMenuRepository.getBentoMenuByHistId(companyId,historyID);

                //update
                bentoMenu.setClosingTime(bentoReservationClosingTime);

                bentoMenuRepository.update(bentoMenu);
            }
        }

        @Override
        public void inSert(BentoReservationSetting bentoReservationSetting) {
            reservationSettingRepository.add(bentoReservationSetting);
        }

        @Override
        public void update(BentoReservationSetting bentoReservationSetting) {
            reservationSettingRepository.update(bentoReservationSetting);
        }
    }
}
