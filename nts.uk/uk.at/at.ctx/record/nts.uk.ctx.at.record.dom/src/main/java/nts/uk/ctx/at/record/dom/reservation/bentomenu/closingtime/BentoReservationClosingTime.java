package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.Optional;

import lombok.Getter;

/**
 * 弁当の予約締め時刻
 * @author Doan Duy Hung
 *
 */
public class BentoReservationClosingTime {
	
	/**
	 * 締め時刻1
	 */
	@Getter
	private final ReservationClosingTime closingTime1;
	
	/**
	 * 締め時刻2
	 */
	@Getter
	private final Optional<ReservationClosingTime> closingTime2;
	
	public BentoReservationClosingTime(ReservationClosingTime closingTime1, Optional<ReservationClosingTime> closingTime2) {
		this.closingTime1 = closingTime1;
		this.closingTime2 = closingTime2;
	}
}
