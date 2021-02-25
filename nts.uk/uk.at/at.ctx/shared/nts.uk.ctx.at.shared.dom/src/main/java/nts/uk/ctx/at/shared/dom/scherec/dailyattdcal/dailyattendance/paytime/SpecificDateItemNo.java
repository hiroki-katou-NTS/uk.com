package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime;

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
