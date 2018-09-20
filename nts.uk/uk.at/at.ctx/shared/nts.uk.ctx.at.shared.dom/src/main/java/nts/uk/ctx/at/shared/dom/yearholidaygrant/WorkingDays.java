package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 年間日数
 */
@HalfIntegerRange(max = 366, min = 0)
public class WorkingDays extends HalfIntegerPrimitiveValue<WorkingDays> {

	private static final long serialVersionUID = 1L;

	public WorkingDays(Double rawValue) {
		super(rawValue);
	}
}
