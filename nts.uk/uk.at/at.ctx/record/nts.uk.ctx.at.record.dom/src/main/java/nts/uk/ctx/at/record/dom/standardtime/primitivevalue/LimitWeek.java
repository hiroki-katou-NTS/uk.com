package nts.uk.ctx.at.record.dom.standardtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author nampt
 *
 */
@TimeRange(max="168:00", min = "00:00")
public class LimitWeek extends TimeDurationPrimitiveValue<LimitWeek> {

	public LimitWeek(int rawValue) {
		super(rawValue);
	}


	private static final long serialVersionUID = 1L;

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 168 * 60) rawValue = 168 * 60;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
