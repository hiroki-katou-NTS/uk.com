package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 特別休暇コード */
@IntegerRange(min = 1, max = 20)
public class SpecialHolidayCode extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SpecialHolidayCode(int rawValue) {
		super(rawValue);
	}
}
