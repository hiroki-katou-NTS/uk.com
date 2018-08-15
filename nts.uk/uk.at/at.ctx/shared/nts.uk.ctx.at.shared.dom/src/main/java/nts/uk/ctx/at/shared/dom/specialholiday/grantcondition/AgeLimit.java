package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 周期
 * 
 * @author tanlv
 *
 */
@IntegerRange(min = 0, max = 99)
public class AgeLimit extends IntegerPrimitiveValue<AgeLimit> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public AgeLimit(Integer rawValue) {
		super(rawValue);
	}
}
