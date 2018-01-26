package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 0, max = 150)
public class LimitAgeFrom extends IntegerPrimitiveValue<LimitAgeFrom> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public LimitAgeFrom(int rawValue) {
		super(rawValue);
	}
}
