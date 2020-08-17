package nts.uk.ctx.exio.dom.exo.condset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/*
 * 日付調整
 */
@IntegerRange(min = -31, max = 31)
public class DateAdjustment extends IntegerPrimitiveValue<DateAdjustment> {

	private static final long serialVersionUID = 1L;

	public DateAdjustment(int rawValue) {
		super(rawValue);
	}

}
