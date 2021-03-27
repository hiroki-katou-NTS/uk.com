package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 36協定年間超過回数
 */
@IntegerRange(max = 12, min = 0)
public class NumberOfMonthShare extends IntegerPrimitiveValue<NumberOfMonthShare> {

	public NumberOfMonthShare(Integer rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
