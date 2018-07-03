package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;


/* 連続休暇チェック日数 */
@HalfIntegerRange(min = 0, max = 99)
public class ContinuousVacationDays extends IntegerPrimitiveValue<ContinuousVacationDays> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContinuousVacationDays(Integer rawValue) {
		super(rawValue);
	}

}
