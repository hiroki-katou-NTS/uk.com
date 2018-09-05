package nts.uk.ctx.exio.dom.qmm.paymentItemset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author thanh.tq 限度金額
 *
 */
@IntegerMaxValue(999999999)
@IntegerMinValue(1)
public class LimitAmount extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public LimitAmount(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
