package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min=0, max=99.5)
public class GrantDays extends HalfIntegerPrimitiveValue<GrantDays> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantDays(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
