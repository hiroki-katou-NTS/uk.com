package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongRange;

/**
 * 
 * @author HungTT - 支払額
 *
 */
@LongRange(min = 0L, max = 9999999999L)
public class PaymentAmount extends LongPrimitiveValue<PaymentAmount> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentAmount(Long rawValue) {
		super(rawValue);
	}

}
