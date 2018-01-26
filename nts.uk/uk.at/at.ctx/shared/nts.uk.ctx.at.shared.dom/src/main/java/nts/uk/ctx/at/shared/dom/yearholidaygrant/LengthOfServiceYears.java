package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 99)
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
