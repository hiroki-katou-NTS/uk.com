package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 賃金テーブル支給金額
 */
@LongMinValue(-9999999999L)
@LongMaxValue(9999999999L)
public class WageTablePaymentAmount extends LongPrimitiveValue<WageTablePaymentAmount> {

	private static final long serialVersionUID = 1L;

	public WageTablePaymentAmount(long rawValue) {
		super(rawValue);
	}
}
