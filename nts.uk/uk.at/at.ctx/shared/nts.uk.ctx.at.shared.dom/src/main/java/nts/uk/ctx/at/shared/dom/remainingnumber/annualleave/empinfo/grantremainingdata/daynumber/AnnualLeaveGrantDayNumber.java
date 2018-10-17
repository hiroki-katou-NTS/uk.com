package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0, max = 99.5)
public class AnnualLeaveGrantDayNumber extends HalfIntegerPrimitiveValue<AnnualLeaveGrantDayNumber>{

	private static final long serialVersionUID = 6651196653684992015L;

	public AnnualLeaveGrantDayNumber(Double rawValue) {
		super(rawValue);
	}

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99.5) rawValue = 99.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
