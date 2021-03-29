package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * アラーム日数
 *
 */
@HalfIntegerRange(min = 0, max = 999.99)
public class ScheAnnualCheckedTimesValueDay  extends HalfIntegerPrimitiveValue<ScheAnnualCheckedTimesValueDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public ScheAnnualCheckedTimesValueDay(Double rawValue) {
		super(rawValue);
	}
	
}