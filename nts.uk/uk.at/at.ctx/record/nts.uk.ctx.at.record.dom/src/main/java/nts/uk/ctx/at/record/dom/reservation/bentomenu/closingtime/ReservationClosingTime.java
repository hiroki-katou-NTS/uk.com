package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
/**
 * 予約締め時刻
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReservationClosingTime extends ValueObject {
	
	/**
	 * 名前
	 */
	private final BentoReservationTimeName reservationTimeName; 
	
	/**
	 * 終了
	 */
	private final BentoReservationTime finish;
	
	/**
	 * 開始
	 */
	private final Optional<BentoReservationTime> start;
	
	public ReservationClosingTime(BentoReservationTimeName reservationTimeName, BentoReservationTime finish, Optional<BentoReservationTime> start) {
		this.reservationTimeName = reservationTimeName;
		this.finish = finish;
		this.start = start;
	}
	
	/**
	 * 予約できるか
	 * @param time
	 * @return
	 */
	public boolean canReserve(ClockHourMinute time) {
		boolean startOK = true;
		boolean endOK = false;
		if(start.isPresent()) {
			startOK = start.get().lessThanOrEqualTo(time.v());
		}
		endOK = time.lessThanOrEqualTo(finish.v());
		return startOK && endOK;
	}
}
