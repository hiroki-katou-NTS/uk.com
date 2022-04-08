package nts.uk.ctx.at.record.dom.reservation.bento;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

/**
 * 予約対象日
 * @author Doan Duy Hung
 *
 */
public class ReservationDate extends ValueObject {
	
	/**
	 * 年月日
	 */
	@Getter
	private final GeneralDate date;
	
	/**
	 * 受付時間帯NO
	 */
	@Getter
	private final ReservationClosingTimeFrame closingTimeFrame;
	
	public ReservationDate(GeneralDate date, ReservationClosingTimeFrame closingTimeFrame) {
		this.date = date;
		this.closingTimeFrame = closingTimeFrame;
	}
	
	/**
	 * 過去日か
	 * @return
	 */
	public boolean isPastDay() {
		return date.before(GeneralDate.today());
	}
	
	/**
	 * 当日か
	 * @return
	 */
	public boolean isToday() {
		return date.equals(GeneralDate.today());
	}
}
