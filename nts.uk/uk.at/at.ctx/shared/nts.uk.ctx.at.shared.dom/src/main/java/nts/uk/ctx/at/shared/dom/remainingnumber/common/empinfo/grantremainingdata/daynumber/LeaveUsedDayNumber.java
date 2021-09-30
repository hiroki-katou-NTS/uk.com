package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 月別休暇使用日数
 * @author masaaki_jinno
 *
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class LeaveUsedDayNumber extends HalfIntegerPrimitiveValue<LeaveGrantDayNumber>{

	//private static final long serialVersionUID = -8576217329914710255L;

	private static final long serialVersionUID = 8330575130887144971L;

	public LeaveUsedDayNumber(Double rawValue) {
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