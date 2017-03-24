package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;

@DecimalMaxValue("9999999999")
public class PayMoney extends DecimalPrimitiveValue<PayMoney> {

	public PayMoney(BigDecimal rawValue) {
		super(rawValue);
	}


}
