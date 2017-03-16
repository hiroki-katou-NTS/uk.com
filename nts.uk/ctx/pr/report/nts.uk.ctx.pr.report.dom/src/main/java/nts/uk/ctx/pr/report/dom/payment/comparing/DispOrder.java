package nts.uk.ctx.pr.report.dom.payment.comparing;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(max = "9999", min = "0")
public class DispOrder extends DecimalPrimitiveValue<DispOrder> {

	private static final long serialVersionUID = 1L;

	public DispOrder(BigDecimal rawValue) {
		super(rawValue);
	}

}
