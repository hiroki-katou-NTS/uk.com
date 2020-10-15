package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author nampt
 * 短時間勤務枠NO
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(2)
public class ShortWorkTimFrameNo extends IntegerPrimitiveValue<ShortWorkTimFrameNo> {

	private static final long serialVersionUID = 1L;
	
	public ShortWorkTimFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
