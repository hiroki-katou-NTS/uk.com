package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 99)
public class SpecialVacationYears extends IntegerPrimitiveValue<SpecialVacationYears> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SpecialVacationYears(int rawValue) {
		super(rawValue);
	} 
}
