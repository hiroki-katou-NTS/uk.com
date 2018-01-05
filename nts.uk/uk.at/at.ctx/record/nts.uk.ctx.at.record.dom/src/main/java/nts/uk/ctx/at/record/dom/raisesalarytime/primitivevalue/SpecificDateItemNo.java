package nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author nampt
 * 外出枠NO
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(10)
public class SpecificDateItemNo extends IntegerPrimitiveValue<SpecificDateItemNo> {
	
	private static final long serialVersionUID = 1L;
	
	public SpecificDateItemNo(Integer rawValue) {
		super(rawValue);
	}

}
