package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class DeleteReservationCorrectCommandHandler {
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    public List<RegisterErrorMessage> delete(List<BentoReservationWithEmpCommand> bentoReservations) {
        List<RegisterErrorMessage> exceptions = new ArrayList<RegisterErrorMessage>();
        List<AtomTask> result = new ArrayList<AtomTask>();
        
        // 1: get(会社ID=ログイン会社ID)
        ReservationSetting setting = reservationSettingRepository.findByCId(AppContexts.user().companyId()).get();
        
        // 2: 社員は予約内容を修正できるか(char, 年月日, 年月日, 時刻時分, int, boolean, 予約受付時間帯)
        // ロールID=ログイン者の就業ロールID, 注文日=システム日付, 予約日=ループ中の弁当予約．予約日, 予約時刻=システム日時, 枠NO=ループ中弁当予約．予約対象日．締め時刻枠, 発注区分=ループ中の弁当予約．発注済み,予約受付時間帯=取得した予約設定．予約受付時間帯(ループ中弁当予約．予約対象日．締め時刻枠）
        bentoReservations.forEach(s -> {
            boolean flag = setting.getCorrectionContent().canEmployeeChangeReservation(
                    AppContexts.user().roles().forAttendance(), 
                    GeneralDate.fromString(s.getBentorReservation().getReservationDate(), "yyyy/MM/dd"), 
                    ClockHourMinute.now(), 
                    s.getBentorReservation().getClosingTimeFrame(), 
                    GeneralDate.today(), 
                    s.getBentorReservation().isOrdered(), 
                    setting.getReservationRecTimeZoneLst().stream()
                        .filter(x -> x.getFrameNo().value == s.getBentorReservation().getClosingTimeFrame())
                        .findFirst().get());
            
            /**
             * ・Falseの場合
                            　       ・リストエラーを作成する
                                    ・Trueの場合
                            　・リスト登録出来る弁当予約を作成する
             */
            if (flag) {
                result.add(AtomTask.of(() -> {
                    bentoReservationRepository.deleteByPK(
                            s.getBentorReservation().getReservationCardNo(), 
                            s.getBentorReservation().getReservationDate(), 
                            s.getBentorReservation().getClosingTimeFrame());
                }));
            } else {
                String param0 = s.getEmployeeCode() + " " + s.getEmployeeName();
                String param1 = s.getBentorReservation().getReservationDate();
                
                ReservationRecTime receptionHours = setting.getReservationRecTimeZoneLst().stream()
                        .filter(x -> x.getFrameNo().value == s.getBentorReservation().getClosingTimeFrame())
                        .findFirst().get().getReceptionHours();
                String param2 = receptionHours.getReceptionName().v() 
                        + " " 
                        + new TimeWithDayAttr(receptionHours.getStartTime().v()).getInDayTimeWithFormat() 
                        + "～" 
                        + new TimeWithDayAttr(receptionHours.getEndTime().v()).getInDayTimeWithFormat();
                
                exceptions.add(new RegisterErrorMessage("Msg_2258", Arrays.asList(param0, param1, param2)));
            }
        });
        
        result.forEach(atomTask -> {
            atomTask.run();
        });
        
        if (exceptions.size() > 0) {
            return exceptions;
        }
        return null;
    }
}
