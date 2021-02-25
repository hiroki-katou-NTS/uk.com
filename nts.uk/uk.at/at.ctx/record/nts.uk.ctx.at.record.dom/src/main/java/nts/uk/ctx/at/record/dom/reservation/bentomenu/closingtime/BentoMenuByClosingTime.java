package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.List;

import lombok.Getter;
import nts.arc.time.clock.ClockHourMinute;

/**
 * 	締め時刻別の弁当メニュー
 * @author Doan Duy Hung
 *
 */
public class BentoMenuByClosingTime {
	
	/**
	 * メニュー1
	 */
	@Getter
	private final List<BentoItemByClosingTime> menu1;
	
	/**
	 * メニュー2
	 */
	@Getter
	private final List<BentoItemByClosingTime> menu2;
	
	/**
	 * 締め時刻
	 */
	@Getter
	private final BentoReservationClosingTime closingTime;
	
	/**
	 * 締め時刻1が受付中
	 */
	@Getter
	private final boolean reservationTime1Atr;
	
	/**
	 * 締め時刻2が受付中
	 */
	@Getter
	private final boolean reservationTime2Atr;
	
	public BentoMenuByClosingTime(List<BentoItemByClosingTime> menu1, List<BentoItemByClosingTime> menu2, BentoReservationClosingTime closingTime,
			boolean reservationTime1Atr, boolean reservationTime2Atr) {
		this.menu1 = menu1;
		this.menu2 = menu2;
		this.closingTime = closingTime;
		this.reservationTime1Atr = reservationTime1Atr;
		this.reservationTime2Atr = reservationTime2Atr;
	}
	
	/**
	 * 現在時刻で作る
	 * @param closingTime
	 * @param menu1
	 * @param menu2
	 * @return
	 */
	public static BentoMenuByClosingTime createForCurrent(BentoReservationClosingTime closingTime, List<BentoItemByClosingTime> menu1, List<BentoItemByClosingTime> menu2) {
		boolean reservationTime1Atr = closingTime.canReserve(
				ReservationClosingTimeFrame.FRAME1, 
				ClockHourMinute.now());
		boolean reservationTime2Atr = closingTime.canReserve(
				ReservationClosingTimeFrame.FRAME2, 
				ClockHourMinute.now());
		return new BentoMenuByClosingTime(menu1, menu2, closingTime, reservationTime1Atr, reservationTime2Atr);
	}
}
