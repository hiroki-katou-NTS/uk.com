package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max=10, min=0)
//喪主時加算日数
public class MorningHour extends IntegerPrimitiveValue<MorningHour> {

	
	private static final long serialVersionUID = 1L;

	public MorningHour(Integer value) {
		super(value);
	}

}
