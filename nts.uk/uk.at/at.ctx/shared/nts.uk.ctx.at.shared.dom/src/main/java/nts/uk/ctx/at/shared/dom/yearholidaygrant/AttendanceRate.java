package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 出勤率
 */
@IntegerRange(max = 100, min = 0)
public class AttendanceRate extends IntegerPrimitiveValue<AttendanceRate> {

	private static final long serialVersionUID = 1L;

	public AttendanceRate(int rawValue) {
		super(rawValue);
	}
}
