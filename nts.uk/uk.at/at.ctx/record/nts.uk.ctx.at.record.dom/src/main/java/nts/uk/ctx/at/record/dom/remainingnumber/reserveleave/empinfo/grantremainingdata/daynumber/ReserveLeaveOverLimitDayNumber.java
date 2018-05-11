package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import lombok.Getter;
import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

@Getter
@HalfIntegerRange(min = 0, max = 99.5)
public class ReserveLeaveOverLimitDayNumber extends HalfIntegerPrimitiveValue<ReserveLeaveOverLimitDayNumber> {

	private static final long serialVersionUID = -8685412044462885135L;

	public ReserveLeaveOverLimitDayNumber(Double rawValue) {
		super(rawValue);
	}

}
