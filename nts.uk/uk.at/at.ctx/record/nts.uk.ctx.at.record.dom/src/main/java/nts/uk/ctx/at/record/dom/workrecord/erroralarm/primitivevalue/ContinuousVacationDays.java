package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/* 連続休暇チェック日数 */
@IntegerMinValue(0)
@IntegerMaxValue(99)
public class ContinuousVacationDays extends IntegerPrimitiveValue<ContinuousVacationDays> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContinuousVacationDays(Integer rawValue) {
		super(rawValue);
	}

}
