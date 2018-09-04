package nts.uk.ctx.exio.dom.qmm.billingitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author thanh.tq 項目名コード
 *
 */
@IntegerMaxValue(9999)
@IntegerMinValue(1)
public class ItemNameCd extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public ItemNameCd(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
