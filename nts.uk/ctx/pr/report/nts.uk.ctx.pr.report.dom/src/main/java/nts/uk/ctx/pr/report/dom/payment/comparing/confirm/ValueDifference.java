package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(max = "9999999999", min = "0")
public class ValueDifference extends DecimalPrimitiveValue<ValueDifference>{

	private static final long serialVersionUID = 1L;

	public ValueDifference(BigDecimal rawValue) {
		super(rawValue);
	}
}
