package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR003_予約の修正.C:予約の新規注文.メニュー別OCD.予約の新規注文を登録する
 *
 */
@Stateless
public class RegisterNewReserCommandHandler {
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    public void register(int frameNo, GeneralDate correctionDate, List<BentoReservation> bentoReservations) {
        // 1: get(会社ID＝ログイン会社ID)
        ReservationSetting setting = reservationSettingRepository.findByCId(AppContexts.user().companyId()).get();
        
        // 1.1: 予約できるか(予約締め時刻枠, 時刻時分, 年月日)
        // 受付時間帯NO=Input．枠NO, 予約時刻＝システム日時, 注文日=Input．注文日
        ReservationRecTimeZone reservationRecTimeZone = setting.getReservationRecTimeZoneLst().stream()
                .filter(x -> x.getFrameNo().value == frameNo).findFirst().get();
//        boolean canRegisterReservation = reservationRecTimeZone.canMakeReservation(EnumAdaptor.valueOf(frameNo, ReservationClosingTimeFrame.class), correctionDate, ClockHourMinute.now());
        boolean canRegisterReservation = setting.getCorrectionContent().canEmployeeChangeReservation(
                AppContexts.user().roles().forAttendance(), 
                GeneralDate.today(), 
                ClockHourMinute.now(), 
                frameNo, 
                correctionDate, 
                false, 
                reservationRecTimeZone);
        
        if (canRegisterReservation) {
            // 予約できるか() == True
            // 2: 弁当予約を登録する
            for (BentoReservation item : bentoReservations) {
                bentoReservationRepository.add(item);
            }
        } else {
            String param0 = correctionDate.toString();
            String param1 = reservationRecTimeZone.getReceptionHours().getReceptionName().v() 
                    + " " + new TimeWithDayAttr(reservationRecTimeZone.getReceptionHours().getStartTime().v()).getInDayTimeWithFormat()
                    + "~" + new TimeWithDayAttr(reservationRecTimeZone.getReceptionHours().getEndTime().v()).getInDayTimeWithFormat();
            
            throw new BusinessException("Msg_2260", param0, param1);
        }
    }
}
