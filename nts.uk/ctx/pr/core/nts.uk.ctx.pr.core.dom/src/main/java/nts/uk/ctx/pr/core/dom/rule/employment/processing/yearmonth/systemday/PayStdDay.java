package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.systemday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 31)
public class PayStdDay extends IntegerPrimitiveValue<PayStdDay> {

	private static final long serialVersionUID = 1L;

	public PayStdDay(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
