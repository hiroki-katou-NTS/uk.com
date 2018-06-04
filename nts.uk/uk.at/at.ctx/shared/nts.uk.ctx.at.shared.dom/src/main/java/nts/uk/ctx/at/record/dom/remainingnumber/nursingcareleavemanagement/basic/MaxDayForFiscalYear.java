package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;

@HalfIntegerRange(min = 0, max = 999.5)
public class MaxDayForFiscalYear extends HalfIntegerPrimitiveValue<MaxDayForFiscalYear> {

	/**
	 * 本年度上限日数
	 * 次年度上限日数
	 */
	private static final long serialVersionUID = 1L;

	public MaxDayForFiscalYear(Double rawValue) {
		super(rawValue);
	}

}