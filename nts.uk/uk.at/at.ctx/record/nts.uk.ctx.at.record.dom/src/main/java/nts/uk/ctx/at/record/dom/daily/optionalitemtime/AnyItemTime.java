package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "-99:59", max = "99:59")
public class AnyItemTime extends TimeDurationPrimitiveValue<AnyItemTime>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemTime(Integer rawValue) {
		super(rawValue);
	}

	@Override
	protected String getPaddedMinutePart() {
		// TODO Auto-generated method stub
		return null;
	}
}