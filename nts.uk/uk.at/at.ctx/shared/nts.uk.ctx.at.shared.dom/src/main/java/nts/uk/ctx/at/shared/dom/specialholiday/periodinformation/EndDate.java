package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 特別休暇付与日数
 * 
 * @author tanlv
 *
 */
@IntegerRange(min = 0, max = 99)
public class EndDate extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public EndDate(int rawValue) {
		super(rawValue);
	}
}
