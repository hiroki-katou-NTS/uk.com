package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
@HalfIntegerRange(min=-9999.9d, max = 9999.9d)
public class ActualSpecialLeaveRemainDay  extends HalfIntegerPrimitiveValue<ActualSpecialLeaveRemainDay>{

	public ActualSpecialLeaveRemainDay(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
