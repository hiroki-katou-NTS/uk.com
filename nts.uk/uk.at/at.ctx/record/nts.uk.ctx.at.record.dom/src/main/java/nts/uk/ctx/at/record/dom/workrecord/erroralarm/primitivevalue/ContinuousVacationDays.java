package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;


/* 連続休暇チェック日数 */
@IntegerRange(min = 0, max = 99)
public class ContinuousVacationDays extends IntegerPrimitiveValue<ContinuousVacationDays> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContinuousVacationDays(Integer rawValue) {
		super(rawValue);
	}

}
