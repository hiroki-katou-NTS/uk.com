package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(max = "999999999999", min = "0")
public class ComparisonValue extends DecimalPrimitiveValue<ComparisonValue>{

	private static final long serialVersionUID = 1L;

	public ComparisonValue(BigDecimal rawValue) {
		super(rawValue);
	}
}
