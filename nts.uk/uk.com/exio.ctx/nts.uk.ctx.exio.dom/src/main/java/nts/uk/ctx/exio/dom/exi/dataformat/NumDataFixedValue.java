package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;
@HalfIntegerMinValue(-999999999.0000)
@HalfIntegerMaxValue(9999999999.9999)
public class NumDataFixedValue extends HalfIntegerPrimitiveValue<NumDataFixedValue>{

	public NumDataFixedValue(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
