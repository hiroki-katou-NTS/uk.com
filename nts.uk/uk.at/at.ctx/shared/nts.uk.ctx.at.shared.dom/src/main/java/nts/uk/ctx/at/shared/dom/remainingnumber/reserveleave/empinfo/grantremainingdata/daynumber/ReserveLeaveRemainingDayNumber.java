package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = -999.5, max = 999.5)
public class ReserveLeaveRemainingDayNumber extends HalfIntegerPrimitiveValue<ReserveLeaveRemainingDayNumber>{
	
	private static final long serialVersionUID = -6054264957973493862L;

	public ReserveLeaveRemainingDayNumber(Double rawValue) {
		super(rawValue);
	}

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999.5) rawValue = 999.5;
		if (rawValue < -999.5) rawValue = -999.5;
		return super.reviseRawValue(rawValue);
	}
}
