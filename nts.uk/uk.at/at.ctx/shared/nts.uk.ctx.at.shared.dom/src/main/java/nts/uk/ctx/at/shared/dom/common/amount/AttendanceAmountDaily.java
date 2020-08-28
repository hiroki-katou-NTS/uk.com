package nts.uk.ctx.at.shared.dom.common.amount;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 勤怠日別金額 */
@IntegerRange(min = -999999, max = 999999)
public class AttendanceAmountDaily extends IntegerPrimitiveValue<AttendanceAmountDaily>{

	/***/
	private static final long serialVersionUID = 1L;

	public AttendanceAmountDaily(Integer rawValue) {
		super(rawValue);
	}
}
