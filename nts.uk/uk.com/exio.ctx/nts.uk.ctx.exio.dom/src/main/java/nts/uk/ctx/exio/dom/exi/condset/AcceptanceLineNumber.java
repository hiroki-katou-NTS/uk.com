package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author HungTT
 * 受入行番号
 *
 */

@IntegerMinValue(1)
@IntegerMaxValue(999999999)
public class AcceptanceLineNumber extends IntegerPrimitiveValue<AcceptanceLineNumber> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptanceLineNumber(Integer rawValue) {
		super(rawValue);
	}

}
