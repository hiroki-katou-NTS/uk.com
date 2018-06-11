package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0, max = 366)
public class YearDayNumber extends HalfIntegerPrimitiveValue<YearDayNumber>{

	private static final long serialVersionUID = 2323206309846311788L;

	public YearDayNumber(Double rawValue) {
		super(rawValue);
	}

}
