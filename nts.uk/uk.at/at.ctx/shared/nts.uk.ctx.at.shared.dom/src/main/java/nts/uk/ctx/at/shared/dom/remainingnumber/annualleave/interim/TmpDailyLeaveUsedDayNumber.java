package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;

/**
 * 日別休暇使用日数
 * @author masaaki_jinno
 *
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class TmpDailyLeaveUsedDayNumber extends HalfIntegerPrimitiveValue<LeaveGrantDayNumber>{

	private static final long serialVersionUID = 8330575130887144971L;

	public TmpDailyLeaveUsedDayNumber(Double rawValue) {
		super(rawValue);
	}

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999.5) rawValue = 999.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
