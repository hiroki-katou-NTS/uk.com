package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 
 * @author HungTT - 支払率
 *
 */
@IntegerRange(min = 0, max = 100)
public class PaymentRate extends IntegerPrimitiveValue<PaymentRate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentRate(Integer rawValue) {
		super(rawValue);
	}

}
