package nts.uk.ctx.at.record.dom.standardtime.primitivevalue;

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
	
	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 744 * 60) rawValue = 744 * 60;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
