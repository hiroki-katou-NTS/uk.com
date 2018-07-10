package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = 0, max = 999.5)
public class AnnualLeaveUsedDayNumber extends HalfIntegerPrimitiveValue<AnnualLeaveGrantDayNumber>{

	private static final long serialVersionUID = -8576217329914710255L;

	public AnnualLeaveUsedDayNumber(Double rawValue) {
		super(rawValue);
	}

}