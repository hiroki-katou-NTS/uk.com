package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
/**
 * 予約締め時刻
 * @author Doan Duy Hung
 *
 */
public class ReservationClosingTime {
	
	/**
	 * 名前
	 */
	@Getter
	private final BentoReservationTimeName reservationTimeName; 
	
	/**
	 * 終了
	 */
	@Getter
	private final BentoReservationTime finish;
	
	/**
	 * 開始
	 */
	@Getter
	private final Optional<BentoReservationTime> start;
	
	public ReservationClosingTime(BentoReservationTimeName reservationTimeName, BentoReservationTime finish, Optional<BentoReservationTime> start) {
		this.reservationTimeName = reservationTimeName;
		this.finish = finish;
		this.start = start;
	}
}
