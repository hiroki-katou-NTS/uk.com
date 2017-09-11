package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;


@IntegerMaxValue(99)
public class Months extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Months(int rawValue) {
		super(rawValue);
	} 
}