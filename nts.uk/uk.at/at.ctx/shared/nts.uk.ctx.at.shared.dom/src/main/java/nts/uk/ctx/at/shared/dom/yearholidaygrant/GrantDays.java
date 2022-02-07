package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;

@HalfIntegerRange(min=0, max=99.5)
public class GrantDays extends HalfIntegerPrimitiveValue<GrantDays> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantDays(Double rawValue) {
		super(rawValue);
	}
	

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99.5) rawValue = 99.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
	
	
	public LeaveGrantDayNumber toLeaveGrantDayNumber(){
		return new LeaveGrantDayNumber(this.v());
	}

}
