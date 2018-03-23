package nts.uk.ctx.at.record.dom.calculationsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(0)
@IntegerMaxValue(10)
public class MaxGoOut extends IntegerPrimitiveValue<MaxGoOut> {

	public MaxGoOut(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

