package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

/**
 * 予約対象日
 * @author Doan Duy Hung
 *
 */
public class ReservationDate {
	
	/**
	 * 年月日
	 */
	@Getter
	private final GeneralDate date;
	
	/**
	 * 締め時刻枠
	 */
	@Getter
	private final ReservationClosingTimeFrame closingTimeFrame;
	
	public ReservationDate(GeneralDate date, ReservationClosingTimeFrame closingTimeFrame) {
		this.date = date;
		this.closingTimeFrame = closingTimeFrame;
	}
}
