package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(99)
public class LimitedHalfHdCnt extends IntegerPrimitiveValue<LimitedHalfHdCnt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimitedHalfHdCnt(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
