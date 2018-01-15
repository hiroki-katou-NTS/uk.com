package nts.uk.ctx.at.record.dom.standardtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author nampt
 *
 */
@TimeRange(max="1488:00", min = "00:00")
public class ErrorTwoMonths extends TimeDurationPrimitiveValue<ErrorTwoMonths> {
	
	public ErrorTwoMonths(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
