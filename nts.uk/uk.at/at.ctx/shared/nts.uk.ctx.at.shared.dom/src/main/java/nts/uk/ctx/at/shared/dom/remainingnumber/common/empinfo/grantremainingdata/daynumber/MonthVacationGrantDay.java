package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * @author thanh_nx
 * 
 *         月別休暇付与日数
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class MonthVacationGrantDay extends HalfIntegerPrimitiveValue<MonthVacationGrantDay> {
	private static final long serialVersionUID = 1L;

	public MonthVacationGrantDay(Double rawValue) {
		super(rawValue);
	}
	
	/**
	 * 0日に切り捨てる
	 */
	public static MonthVacationGrantDay createWithTruncate(Double value) {
		return new MonthVacationGrantDay(Math.floor(value));
	}

	/**
	 * 1日に切り上げる
	 */
	public static MonthVacationGrantDay createWithRoundUp(Double value) {
		return new MonthVacationGrantDay(Math.ceil(value));
	}
}
