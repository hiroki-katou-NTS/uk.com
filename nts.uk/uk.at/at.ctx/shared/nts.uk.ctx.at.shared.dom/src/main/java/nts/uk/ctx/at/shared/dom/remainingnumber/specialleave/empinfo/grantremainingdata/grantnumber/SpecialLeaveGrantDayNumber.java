package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import java.io.Serializable;

import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;

@HalfIntegerRange(min = 0, max = 99.5)
public class SpecialLeaveGrantDayNumber extends LeaveGrantDayNumber implements Serializable{

	private static final long serialVersionUID = 6651196653684992015L;

	public SpecialLeaveGrantDayNumber(Double rawValue) {
		super(rawValue);
	}

//	@Override
//	protected Double reviseRawValue(Double rawValue) {
//		if (rawValue == null) return super.reviseRawValue(rawValue);
//		if (rawValue > 99.5) rawValue = 99.5;
//		if (rawValue < 0.0) rawValue = 0.0;
//		return super.reviseRawValue(rawValue);
//	}
}
