package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class NumberDayNursing.
 * 子の看護介護上限日数
 */
@IntegerRange(max = 99, min = 0)
public class ChildCareNurseUpperLimit extends IntegerPrimitiveValue<ChildCareNurseUpperLimit> {

	private static final long serialVersionUID = 1L;

	public ChildCareNurseUpperLimit(Integer rawValue) {
		super(rawValue);
	}

}