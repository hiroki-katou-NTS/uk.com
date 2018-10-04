package nts.uk.ctx.at.request.dom.application.overtime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 12, min = 0)
public class NumberOfMonth extends IntegerPrimitiveValue<NumberOfMonth> {

	public NumberOfMonth(Integer rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
