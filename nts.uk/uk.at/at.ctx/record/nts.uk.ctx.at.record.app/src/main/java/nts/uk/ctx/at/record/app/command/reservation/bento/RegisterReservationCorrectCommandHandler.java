package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class RegisterReservationCorrectCommandHandler {
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    public List<RegisterErrorMessage> register(List<BentoReservationWithEmp> bentoReservations) {
        List<RegisterErrorMessage> exceptions = new ArrayList<RegisterErrorMessage>();
        List<AtomTask> result = new ArrayList<AtomTask>();
        
        // 1: get(会社ID=ログイン会社ID)
        ReservationSetting setting = reservationSettingRepository.findByCId(AppContexts.user().companyId()).get();
        
        List<BentoReservation> bentoReservationLst = new ArrayList<>(); 
        if(!CollectionUtil.isEmpty(bentoReservations)) {
        	bentoReservationLst = bentoReservationRepository.getReservationInformation(
        			bentoReservations.stream().map(x -> x.getBentorReservation().getRegisterInfor()).collect(Collectors.toList()), 
        			bentoReservations.get(0).getBentorReservation().getReservationDate());
        }
        
        // 2: 社員は予約内容を修正できるか(char, 年月日, 年月日, 時刻時分, int, boolean, 予約受付時間帯)
        //      (ロールID=ログイン者の就業ロールID, 注文日=システム日付, 予約日=ループ中の弁当予約．予約日, 予約時刻=システム日時, 枠NO=ループ中弁当予約．予約対象日．締め時刻枠, 発注区分=ループ中の弁当予約．発注済み,予約受付時間帯=取得した予約設定．予約受付時間帯(ループ中弁当予約．予約対象日．締め時刻枠）)
        for(BentoReservationWithEmp s : bentoReservations) {
        	BentoReservation bentoReservation = bentoReservationLst.stream().filter(x -> 
        		x.getRegisterInfor().getReservationCardNo().equals(s.getBentorReservation().getRegisterInfor().getReservationCardNo())).findAny().get();
            boolean flag = setting.getCorrectionContent().canEmployeeChangeReservation(
                    AppContexts.user().roles().forAttendance(), 
                    bentoReservation.getReservationDate().getDate(), 
                    ClockHourMinute.now(), 
                    bentoReservation.getReservationDate().getClosingTimeFrame().value, 
                    GeneralDate.today(), 
                    bentoReservation.isOrdered(), 
                    setting.getReservationRecTimeZoneLst().stream()
                        .filter(x -> x.getFrameNo().value == bentoReservation.getReservationDate().getClosingTimeFrame().value)
                        .findFirst().get());
            
            /**
             * ・Falseの場合
                            　       ・リストエラーを作成する
                                    ・Trueの場合
                            　・リスト登録出来る弁当予約を作成する
             */
            if (flag) {
                result.add(AtomTask.of(() -> {
                    bentoReservationRepository.update(s.getBentorReservation());
                }));
            } else {
                String param0 = s.getEmployeeCode() + " " + s.getEmployeeName();
                String param1 = s.getBentorReservation().getReservationDate().getDate().toString();
                
                ReservationRecTime receptionHours = setting.getReservationRecTimeZoneLst().stream()
                        .filter(x -> x.getFrameNo().value == s.getBentorReservation().getReservationDate().getClosingTimeFrame().value)
                        .findFirst().get().getReceptionHours();
                String param2 = receptionHours.getReceptionName().v() 
                        + " " 
                        + new TimeWithDayAttr(receptionHours.getStartTime().v()).getInDayTimeWithFormat() 
                        + "～" 
                        + new TimeWithDayAttr(receptionHours.getEndTime().v()).getInDayTimeWithFormat();
                
                exceptions.add(new RegisterErrorMessage("Msg_2257", Arrays.asList(param0, param1, param2)));
            }
        }
        
        result.forEach(atomTask -> {
            atomTask.run();
        });
        
        if (exceptions.size() > 0) {
            return exceptions;
        }
        return null;
    }
}
