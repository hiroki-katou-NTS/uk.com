package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約受付時刻
 * @author Doan Duy Hung
 *
 */
public class ReservationRecTime {
	/**
	 * 	受付名称
	 */
	@Getter
	private BentoReservationTimeName receptionName;
	
	/**
	 * 	開始時刻
	 */
	@Getter
	private BentoReservationTime startTime;
	
	/**
	 * 	終了時刻
	 */
	@Getter
	private BentoReservationTime endTime;
	
	public ReservationRecTime(BentoReservationTimeName receptionName, BentoReservationTime startTime, BentoReservationTime endTime) {
		this.receptionName = receptionName;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * 	[1] 予約できるか
	 * @param time 予約時刻
	 * @param date 注文日
	 * @return 予約できるならtrue
	 */
	public boolean canMakeReservation(ClockHourMinute reservationTime, GeneralDate orderDate) {
		// 	$開始OK = @開始時刻 <=　予約時刻
		boolean startOK = this.startTime.valueAsMinutes() <= reservationTime.valueAsMinutes();
		// 	$終了OK = 予約時刻 <= ＠終了時刻
		boolean endOK = reservationTime.valueAsMinutes() <= this.endTime.valueAsMinutes();
		
		GeneralDate systemDate = GeneralDate.today();
		return (orderDate.equals(systemDate) && startOK && endOK) || orderDate.after(systemDate);
	}
}
