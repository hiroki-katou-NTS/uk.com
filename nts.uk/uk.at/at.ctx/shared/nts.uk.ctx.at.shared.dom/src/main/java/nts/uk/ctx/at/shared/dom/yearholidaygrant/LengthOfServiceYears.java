package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(99)
public class LengthOfServiceYears extends IntegerPrimitiveValue<LengthOfServiceYears> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LengthOfServiceYears(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
