package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author thanh.tq 統合コード
 *
 */
@IntegerMaxValue(99)
@IntegerMinValue(1)
public class IntergrateCd extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public IntergrateCd(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
