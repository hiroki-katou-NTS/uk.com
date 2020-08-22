package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

/**
 *  予約構成を追加する
 */
@Stateless
public class BentoMenuHistCommandHandler extends CommandHandler<BentoMenuHistCommand> {
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistotyRepository;

    @Inject
    private  BentomenuAdapter bentomenuAdapter;

    @Inject
    private  BentoMenuRepository bentoMenuRepository;

    @Inject
    private  BentoReservationSettingRepository reservationSettingRepository;


    @Override
    protected void handle(CommandHandlerContext<BentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(bentoMenuHistotyRepository,bentoMenuRepository,reservationSettingRepository);
        val companyId = AppContexts.user().companyId();

        ReservationClosingTime closingTime1 = new ReservationClosingTime(new BentoReservationTimeName("昼食"),
                new BentoReservationTime(120),
                Optional.of(new BentoReservationTime(0)));

        Optional<ReservationClosingTime> closingTime2 = Optional.of(new ReservationClosingTime(
                new BentoReservationTimeName("夕食"),
                new BentoReservationTime(240),
                Optional.of(new BentoReservationTime(180))));

        BentoReservationClosingTime bentoReservationClosingTime = new BentoReservationClosingTime(closingTime1,closingTime2);
        AtomTask persist = BentoMenuHistService.register(require,
                new DatePeriod(command.getDate(), GeneralDate.max()), companyId,bentoReservationClosingTime);

        transaction.execute(() -> {
            persist.run();
        });
    }

    @AllArgsConstructor
    private class RequireImpl implements BentoMenuHistService.Require {

        private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

        private BentoMenuRepository bentoMenuRepository;

        private BentoReservationSettingRepository reservationSettingRepository;

        @Override
        public Optional<BentoMenuHistory> findByCompanyId(String companyId) {
            Optional<BentoMenuHistory> existHist = bentoMenuHistoryRepository.findByCompanyId(companyId);
            return existHist;
        }

        @Override
        public void update(DateHistoryItem item) {
            bentoMenuHistoryRepository.update(BentoMenuHistory.toDomain(AppContexts.user().companyId(), Arrays.asList(item)));
        }

        @Override
        public void add(DateHistoryItem item) {
            bentoMenuHistoryRepository.add(BentoMenuHistory.toDomain(AppContexts.user().companyId(),Arrays.asList(item)));
        }

        @Override
        public void addBentomenu(DateHistoryItem hist, BentoReservationClosingTime bentoReservationClosingTime) {

            Optional<BentoReservationSetting> bentoReservationSetting = reservationSettingRepository.findByCId(AppContexts.user().companyId());
            if (bentoReservationSetting.isPresent()){
                String workLocation = null;

                if (bentoReservationSetting.get().getOperationDistinction().value == OperationDistinction.BY_LOCATION.value){
                    val data = bentomenuAdapter.findBySid(AppContexts.user().employeeId(),GeneralDate.today());
                    workLocation = data.isPresent() ? data.get().getWorkLocationCd() : null;
                }
                Optional<WorkLocationCode> workLocationCode = workLocation == null ? Optional.empty() : Optional.of(new WorkLocationCode(workLocation));

                Bento bento = new Bento(1,new BentoName("弁当１"),new BentoAmount(1000),
                        new BentoAmount(0),new BentoReservationUnitName("円"),
                        true,false,workLocationCode);
                bentoMenuRepository.add(new BentoMenu(hist.identifier() , Arrays.asList(bento),bentoReservationClosingTime));
            }
        }
    }
}

