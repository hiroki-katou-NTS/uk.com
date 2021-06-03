package nts.uk.ctx.exio.dom.exo.dataformat.init;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * @author TamNX
 * データ形式文字桁
 */
@IntegerMaxValue(999)
@IntegerMinValue(0)
public class DataFormatCharacterDigit extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public DataFormatCharacterDigit(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
