package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 11)
public class LengthOfServiceMonths extends IntegerPrimitiveValue<LengthOfServiceMonths> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LengthOfServiceMonths(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
