package nts.uk.ctx.at.shared.dom.standardtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author nampt
 *
 */
@TimeRange(max="8784:00", min = "00:00")
public class LimitOneYear extends TimeDurationPrimitiveValue<LimitOneYear> {
	
	public LimitOneYear(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
