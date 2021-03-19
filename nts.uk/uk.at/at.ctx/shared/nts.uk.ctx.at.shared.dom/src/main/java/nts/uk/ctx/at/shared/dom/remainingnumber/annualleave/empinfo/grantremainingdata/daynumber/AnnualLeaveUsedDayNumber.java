package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.io.Serializable;

import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;

/**
 * 年休使用日数
 * @author masaaki_jinno
 *
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class AnnualLeaveUsedDayNumber extends LeaveUsedDayNumber implements Serializable {

	private static final long serialVersionUID = -8576217329914710255L;

	public AnnualLeaveUsedDayNumber(Double rawValue) {
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