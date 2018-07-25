package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
@IntegerRange(max=999, min=0)
public class FixedDayGrant extends IntegerPrimitiveValue<FixedDayGrant> {

	
	private static final long serialVersionUID = 1L;

	public FixedDayGrant(Integer value) {
		super(value);
	}

}
