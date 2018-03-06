package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 有効桁数開始桁
 */
@IntegerMaxValue(9999)
@IntegerMinValue(0)
public class EndDigit extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public EndDigit(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
