package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 2)
public class PCLogOnNo extends IntegerPrimitiveValue<PCLogOnNo> {

	/***/
	private static final long serialVersionUID = 1L;

	public PCLogOnNo(Integer rawValue) {
		super(rawValue);
	}

}
