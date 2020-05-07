package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;;

//@HalfIntegerRange(min = -999.5 , max = 999.5)
public class AnnualLeaveRemainingDayNumber extends LeaveRemainingDayNumber{

	private static final long serialVersionUID = 8578961613409044770L;

	public AnnualLeaveRemainingDayNumber(Double rawValue) {
		super(rawValue);
	}

//	@Override
//	protected Double reviseRawValue(Double rawValue) {
//		if (rawValue == null) return super.reviseRawValue(rawValue);
//		if (rawValue > 999.5) rawValue = 999.5;
//		if (rawValue < -999.5) rawValue = -999.5;
//		return super.reviseRawValue(rawValue);
//	}
}
