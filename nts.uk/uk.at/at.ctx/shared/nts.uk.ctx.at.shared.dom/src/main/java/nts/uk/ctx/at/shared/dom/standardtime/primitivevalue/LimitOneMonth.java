package nts.uk.ctx.at.shared.dom.standardtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author nampt
 *
 */
@TimeRange(max="744:00", min = "00:00")
public class LimitOneMonth extends TimeDurationPrimitiveValue<LimitOneMonth>{
	
	public LimitOneMonth(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
