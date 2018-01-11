package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 11)
public class SpecialVacationMonths extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SpecialVacationMonths(int rawValue) {
		super(rawValue);
	}
}
