package nts.uk.ctx.exio.dom.dataformat.value;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 有効桁数終了桁
 */
@IntegerMaxValue(9999)
@IntegerMinValue(1)
public class AcceptedDigit extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public AcceptedDigit(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
