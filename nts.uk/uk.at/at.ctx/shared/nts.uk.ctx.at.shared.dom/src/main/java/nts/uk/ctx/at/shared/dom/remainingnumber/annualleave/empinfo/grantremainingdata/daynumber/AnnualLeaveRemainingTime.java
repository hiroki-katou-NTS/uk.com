package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "-999:59", max="999:59")

public class AnnualLeaveRemainingTime extends TimeDurationPrimitiveValue<AnnualLeaveRemainingTime>{

	private static final long serialVersionUID = -3402887695920983416L;

	public AnnualLeaveRemainingTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 999 * 60 + 59) rawValue = 999 * 60 + 59;
		if (rawValue < -(999 * 60 + 59)) rawValue = -(999 * 60 + 59);
		return super.reviseRawValue(rawValue);
	}
}
