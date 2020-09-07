package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.time.clock.ClockHourMinute;

/**
 * 弁当の予約締め時刻
 * @author Doan Duy Hung
 *
 */
public class BentoReservationClosingTime extends ValueObject {
	
	/**
	 * 締め時刻1
	 */
	@Getter
	private ReservationClosingTime closingTime1;
	
	/**
	 * 締め時刻2
	 */
	@Getter
	private Optional<ReservationClosingTime> closingTime2;
	
	public BentoReservationClosingTime(ReservationClosingTime closingTime1, Optional<ReservationClosingTime> closingTime2) {
		this.closingTime1 = closingTime1;
		this.closingTime2 = closingTime2;
	}
	
	/**
	 * 予約できるか
	 * @param timeFrameAtr
	 * @param time
	 * @return
	 */
	public boolean canReserve(ReservationClosingTimeFrame timeFrameAtr, ClockHourMinute time) {
		if(timeFrameAtr==ReservationClosingTimeFrame.FRAME1) {
			return closingTime1.canReserve(time);
		}
		if(timeFrameAtr==ReservationClosingTimeFrame.FRAME2) {
			return closingTime2.map(x -> x.canReserve(time)).orElse(true);
		}
		throw new RuntimeException("System Error");
	}
}
