package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import nts.arc.primitive.TimeAsMinutesPrimitiveValue;

public class AnyItemTime extends TimeAsMinutesPrimitiveValue<AnyItemTime>{

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