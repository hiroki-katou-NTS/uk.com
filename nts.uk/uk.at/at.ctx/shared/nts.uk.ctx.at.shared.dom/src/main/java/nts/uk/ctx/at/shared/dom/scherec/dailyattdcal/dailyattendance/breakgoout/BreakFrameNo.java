package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author nampt
 * 休憩枠NO
 *
 */
@IntegerMaxValue(10)
@IntegerMinValue(1)
public class BreakFrameNo extends IntegerPrimitiveValue<BreakFrameNo> {

	private static final long serialVersionUID = 1L;
	
	public BreakFrameNo(Integer rawValue) {
		super(rawValue);
	}

}
