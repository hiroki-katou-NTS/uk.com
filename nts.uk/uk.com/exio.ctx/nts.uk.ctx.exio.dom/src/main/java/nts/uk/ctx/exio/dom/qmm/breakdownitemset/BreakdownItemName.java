package nts.uk.ctx.exio.dom.qmm.breakdownitemset;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author thanh.tq 内訳項目名称
 *
 */
@StringMaxLength(30)
public class BreakdownItemName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public BreakdownItemName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
