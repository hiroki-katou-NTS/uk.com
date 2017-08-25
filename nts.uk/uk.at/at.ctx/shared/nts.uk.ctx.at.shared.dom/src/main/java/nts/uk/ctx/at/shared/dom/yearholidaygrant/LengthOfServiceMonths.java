package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
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
