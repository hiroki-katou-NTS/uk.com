package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import lombok.Getter;
import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@Getter
@HalfIntegerRange(min = 0, max = 999.5)
public class ReserveLeaveUsedDayNumber extends HalfIntegerPrimitiveValue<ReserveLeaveUsedDayNumber> {

	private static final long serialVersionUID = -8685412044462885135L;

	public ReserveLeaveUsedDayNumber(Double rawValue) {
		super(rawValue);
	}

}
