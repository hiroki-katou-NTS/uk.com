package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;

/**
 * 弁当予約時刻
 * @author Doan Duy Hung
 *
 */
@TimeMaxValue("23:59")
@TimeMinValue("0:00")
public class BentoReservationTime extends TimeClockPrimitiveValue<BentoReservationTime>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BentoReservationTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
