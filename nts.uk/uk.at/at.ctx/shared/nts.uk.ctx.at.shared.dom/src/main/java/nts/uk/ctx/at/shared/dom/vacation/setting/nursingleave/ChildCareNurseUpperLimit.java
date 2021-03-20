package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0d, max = 999.5)
public class ChildCareNurseUpperLimit extends HalfIntegerPrimitiveValue<ChildCareNurseUpperLimit> {

	/***/
	private static final long serialVersionUID = 1L;

	public ChildCareNurseUpperLimit(Double rawValue) {
		super(rawValue);
	}
}