package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

/**
 * 年休残日数
 * */

import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;;

//@HalfIntegerRange(min = -999.5 , max = 999.5)
public class AnnualLeaveRemainingDayNumber extends LeaveRemainingDayNumber{

	private static final long serialVersionUID = 8578961613409044770L;

	public AnnualLeaveRemainingDayNumber(Double rawValue) {
		super(rawValue);
	}
}
