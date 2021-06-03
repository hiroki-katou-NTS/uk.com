package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 少数桁数
 */
@IntegerMaxValue(10)
@IntegerMinValue(0)
public class DecimalDigitNumber extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public DecimalDigitNumber(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
