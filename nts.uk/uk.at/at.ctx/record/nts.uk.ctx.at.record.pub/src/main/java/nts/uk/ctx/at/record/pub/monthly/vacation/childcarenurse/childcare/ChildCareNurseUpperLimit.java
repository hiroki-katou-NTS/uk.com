package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 子の看護介護上限日数
  * @author yuri_tamakoshi
 */
@HalfIntegerRange(min = 0d, max = 999.5)
public class ChildCareNurseUpperLimit extends HalfIntegerPrimitiveValue<ChildCareNurseUpperLimit> {

	/***/
	private static final long serialVersionUID = 1L;

	public ChildCareNurseUpperLimit(Double rawValue) {
		super(rawValue);
	}

}
