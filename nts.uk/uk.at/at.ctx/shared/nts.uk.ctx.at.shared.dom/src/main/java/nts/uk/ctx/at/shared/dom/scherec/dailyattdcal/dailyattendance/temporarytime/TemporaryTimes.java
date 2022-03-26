package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 臨時回数
 * @author shuichi_ishida
 */
@IntegerMinValue(0)
@IntegerMaxValue(10)
public class TemporaryTimes extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TemporaryTimes(Integer rawValue) {
		super(rawValue);
	}
}
