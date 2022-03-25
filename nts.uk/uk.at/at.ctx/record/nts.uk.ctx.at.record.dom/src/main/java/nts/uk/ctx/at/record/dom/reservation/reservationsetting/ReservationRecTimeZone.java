package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約受付時間帯
 * @author Doan Duy Hung
 *
 */
public class ReservationRecTimeZone {
	
	/**
	 * 	受付時間帯
	 */
	@Getter
	private ReservationRecTime receptionHours;
	
	/**
	 * 	枠NO
	 */
	@Getter
	private final ReservationClosingTimeFrame frameNo;
	
	public ReservationRecTimeZone(ReservationRecTime receptionHours, ReservationClosingTimeFrame frameNo) {
		this.receptionHours = receptionHours;
		this.frameNo = frameNo;
	}
	
	/**
	 * [1] 予約できるか
	 * @param frameNo 枠NO
	 * @param orderDate 注文日
	 * @param reservationTime 予約時刻
	 * @return 予約できるならtrue
	 */
	public boolean canMakeReservation(ReservationClosingTimeFrame frameNo, GeneralDate orderDate, ClockHourMinute reservationTime) {
		return this.frameNo==frameNo && this.receptionHours.canMakeReservation(reservationTime, orderDate);
	}
}
