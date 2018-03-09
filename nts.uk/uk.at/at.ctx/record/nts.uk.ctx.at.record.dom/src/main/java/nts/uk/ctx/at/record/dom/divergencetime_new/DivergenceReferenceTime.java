package nts.uk.ctx.at.record.dom.divergencetime_new;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class DivergenceReferenceTime.
 */
@TimeRange(max = "99:59", min = "00:00")
public class DivergenceReferenceTime extends TimeDurationPrimitiveValue<DivergenceReferenceTime>{

	/**
	 * Instantiates a new divergence reference time.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public DivergenceReferenceTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
