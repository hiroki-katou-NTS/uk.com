package nts.uk.ctx.at.record.dom.standardtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author nampt
 *
 */
@TimeRange(max="8784:00", min = "00:00")
public class ErrorOneYear extends TimeDurationPrimitiveValue<ErrorOneYear>{
	
	public ErrorOneYear(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
	
}
