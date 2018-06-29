package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

//年
@IntegerMinValue(1)
@IntegerMaxValue(9999)
public class Year extends IntegerPrimitiveValue<Year>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Year(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
