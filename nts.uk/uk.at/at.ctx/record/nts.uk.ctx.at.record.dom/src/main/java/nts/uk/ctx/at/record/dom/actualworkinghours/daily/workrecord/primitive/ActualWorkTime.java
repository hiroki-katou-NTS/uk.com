package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive;

import nts.arc.primitive.TimeAsMinutesPrimitiveValue;

public class ActualWorkTime extends TimeAsMinutesPrimitiveValue<ActualWorkTime>{

	private static final long serialVersionUID = 1L;

	public ActualWorkTime(Integer rawValue) {
		super(rawValue);
	}

	@Override
	protected String getPaddedMinutePart() {
		// TODO Auto-generated method stub
		return null;
	}

}
