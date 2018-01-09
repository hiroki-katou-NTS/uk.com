package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.IntegerRange;


@IntegerRange(min = 0, max = 999)
public class FixNumberDays extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public FixNumberDays(int rawValue) {
		super(rawValue);
	} 
}