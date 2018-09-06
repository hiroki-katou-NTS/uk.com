package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author thanh.tq 内訳項目コード
 *
 */
@IntegerMaxValue(99)
@IntegerMinValue(1)
public class BreakdownItemCode extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public BreakdownItemCode(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
