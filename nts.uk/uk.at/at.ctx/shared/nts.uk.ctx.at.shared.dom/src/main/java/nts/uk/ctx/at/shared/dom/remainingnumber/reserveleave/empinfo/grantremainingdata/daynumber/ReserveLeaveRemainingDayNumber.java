package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@HalfIntegerRange(min = -999.5, max = 999.5)
public class ReserveLeaveRemainingDayNumber extends HalfIntegerPrimitiveValue<ReserveLeaveRemainingDayNumber>{
	
	private static final long serialVersionUID = -6054264957973493862L;

	public ReserveLeaveRemainingDayNumber(Double rawValue) {
		super(rawValue);
	}

}
