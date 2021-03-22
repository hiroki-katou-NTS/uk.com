package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0, max = 99)
public class ChildCareNurseUpperLimit extends IntegerPrimitiveValue<ChildCareNurseUpperLimit> {

	private static final long serialVersionUID = 1L;

	public ChildCareNurseUpperLimit(Integer rawValue) {
		super(rawValue);
	}

}