package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 11)
public class SpecialVacationMonths extends IntegerPrimitiveValue<SpecialVacationMonths> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SpecialVacationMonths(int rawValue) {
		super(rawValue);
	}
}
