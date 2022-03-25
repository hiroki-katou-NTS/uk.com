package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約修正内容
 */

@AllArgsConstructor
@Getter
public class CorrectionContent extends DomainObject {

    /**
     * 予約済みの内容変更期限
     */
    // Content change deadline.
    private ContentChangeDeadline contentChangeDeadline;

    /**
     * 予約済みの内容変更期限日数
     */
    // content change deadline days.
    private ContentChangeDeadlineDay contentChangeDeadlineDay;

    /**
     * 発注機能管理区分
     */
    // Ordered data.
    private ReservationOrderMngAtr orderMngAtr;
    
    /**
     * 修正不可の際でも修正可能な権限一覧
     */
    private List<String> canModifiLst;
    
    /**
     * 	[1] 予約内容修正できるか
     * @param reservationDate 予約日
     * @param reservationTime 予約時刻
     * @param frameNo 枠NO
     * @param orderDate 注文日
     * @param reservationRecTimeZone 予約受付時間帯
     * @return 修正できるならTrue
     */
    public boolean canChangeReservationDetail(GeneralDate reservationDate, ClockHourMinute reservationTime, int frameNo, GeneralDate orderDate,
    		ReservationRecTimeZone reservationRecTimeZone) {
    	if(this.contentChangeDeadline==ContentChangeDeadline.ALLWAY_FIXABLE) {
    		return true;
    	}
    	if(this.contentChangeDeadline==ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR) {
    		return this.canChangeReservationDuringTime(reservationTime, frameNo, orderDate, reservationRecTimeZone);
    	}
    	return this.canChangeReservationFromOrderDate(orderDate);
    }
    
    /**
     * 	[2] 社員は予約内容を修正できるか
     * @param roleID ロールID
     * @param reservationDate 予約日
     * @param reservationTime 予約時刻
     * @param frameNo 枠NO
     * @param orderDate 注文日
     * @param orderAtr 発注区分
     * @return 修正できるならTrue
     */
    public boolean canEmployeeChangeReservation(String roleID, GeneralDate reservationDate, ClockHourMinute reservationTime, int frameNo,
    		GeneralDate orderDate, boolean orderAtr, ReservationRecTimeZone reservationRecTimeZone) {
    	if(orderAtr) {
    		return this.roleCanModifi(roleID);
    	}
    	if(this.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)) {
    		return true;
    	}
    	return this.roleCanModifi(roleID);
    }
    
    /**
     * [3]  注文日から予約済みの内容変更できるか
     * @param orderDate 注文日
     * @return 修正できるならTrue
     */
    public boolean canChangeReservationFromOrderDate(GeneralDate orderDate) {
    	return 0 <= orderDate.compareTo(GeneralDate.today()) && 
    			orderDate.compareTo(GeneralDate.today()) <= this.contentChangeDeadlineDay.value;
    }
    
    /**
     * 	[4] 受付時間内は予約内容修正できるか
     * @param reservationTime 予約時刻
     * @param frameNo 枠NO
     * @param orderDate 注文日
     * @param reservationRecTimeZone 予約受付時間帯
     * @return 修正できるならTrue
     */
    public boolean canChangeReservationDuringTime(ClockHourMinute reservationTime, int frameNo, GeneralDate orderDate, ReservationRecTimeZone reservationRecTimeZone) {
    	return orderDate.equals(GeneralDate.today()) &&
    			reservationRecTimeZone.canMakeReservation(
    			EnumAdaptor.valueOf(frameNo, ReservationClosingTimeFrame.class), 
    			orderDate, 
    			reservationTime);
    }
    
    /**
     * [5] 発注機能管理するか
     * @return 管理するならTrue
     */
    public boolean manageOrderFunction() {
    	return this.orderMngAtr == ReservationOrderMngAtr.CAN_MANAGE;
    }
    
    /**
     * [6] 修正可能な権限があるか
     * @param roleID ロールID
     * @return 権限があるならTrue
     */
    public boolean roleCanModifi(String roleID) {
    	return !CollectionUtil.isEmpty(this.canModifiLst) && this.canModifiLst.contains(roleID);
    }

}
