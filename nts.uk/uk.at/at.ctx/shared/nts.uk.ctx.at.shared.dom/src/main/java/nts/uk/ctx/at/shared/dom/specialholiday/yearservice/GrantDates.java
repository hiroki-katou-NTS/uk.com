package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(2)
public class GrantDates extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GrantDates(int rawValue) {
		super(rawValue);
	} 
}
