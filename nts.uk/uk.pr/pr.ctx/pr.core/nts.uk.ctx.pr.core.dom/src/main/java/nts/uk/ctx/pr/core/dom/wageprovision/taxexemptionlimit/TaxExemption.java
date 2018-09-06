package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author thanh.tq 非課税限度額
 *
 */
@IntegerMaxValue(999999999)
@IntegerMinValue(1)
public class TaxExemption extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public TaxExemption(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
