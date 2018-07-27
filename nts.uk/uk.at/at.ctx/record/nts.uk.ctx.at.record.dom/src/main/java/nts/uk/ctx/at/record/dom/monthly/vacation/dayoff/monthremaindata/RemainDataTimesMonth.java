package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
@TimeRange(min ="00:00", max="999:59")
public class RemainDataTimesMonth extends TimeDurationPrimitiveValue<RemainDataTimesMonth> {

	public RemainDataTimesMonth(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
