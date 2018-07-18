package nts.uk.ctx.exio.dom.exo.dataformat.init;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * @author TamNX
 * データ形式整数桁
 */
@IntegerMaxValue(10)
@IntegerMinValue(0)
public class DataFormatIntegerDigit extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public DataFormatIntegerDigit(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
