package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;


@IntegerMaxValue(9999)
public class GrantDate extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GrantDate(int rawValue) {
		super(rawValue);
	} 
}