package nts.uk.ctx.at.request.dom.setting.request.application.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max=7,min=1)
public class NumDaysOfWeek extends IntegerPrimitiveValue<NumDaysOfWeek> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumDaysOfWeek(Integer rawValue) {
		super(rawValue);
	}

}
