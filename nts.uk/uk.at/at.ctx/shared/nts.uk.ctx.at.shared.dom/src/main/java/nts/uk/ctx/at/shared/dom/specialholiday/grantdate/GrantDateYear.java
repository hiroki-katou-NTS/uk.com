package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 99)
public class GrantDateYear extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public GrantDateYear(int rawValue) {
		super(rawValue);
	} 
}