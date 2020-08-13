package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.RegisterReservationLunchService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

        AtomTask persist = RegisterReservationLunchService.register(
                require,command.getOperationDistinction(),command.getAchievements(),command.getCorrectionContent(),command.getClosingTime());
        transaction.execute(() -> {
            persist.run();
        });

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
                List<DateHistoryItem> dateHistoryItems = new ArrayList<>();
                dateHistoryItems.add(hist);
                val itemToBeAdded = new BentoMenuHistory(companyId, dateHistoryItems);

                bentoMenuHistoryRepository.add(itemToBeAdded);

                val newBentoMenu = new BentoMenu(hist.identifier() ,new ArrayList<>(),bentoReservationClosingTime);
                bentoMenuRepository.add(newBentoMenu);
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
