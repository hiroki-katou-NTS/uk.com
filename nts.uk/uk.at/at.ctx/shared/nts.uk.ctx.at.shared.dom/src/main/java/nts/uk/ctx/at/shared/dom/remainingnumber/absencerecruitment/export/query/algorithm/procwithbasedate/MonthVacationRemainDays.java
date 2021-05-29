package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.procwithbasedate;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * @author thanh_nx
 *
 *         月別休暇残日数
 */
@HalfIntegerRange(min = -999.5, max = 999.5)
public class MonthVacationRemainDays extends HalfIntegerPrimitiveValue<MonthVacationRemainDays> {

	/**
	 * 日数
	 */
	private static final long serialVersionUID = 1L;

	public MonthVacationRemainDays(Double rawValue) {
		super(rawValue);
	}

}
