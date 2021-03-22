package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 特別休暇使用日数
 * @author do_dt
 *
 */
@HalfIntegerRange(min=0d, max = 999.5d)
public class SpecialLeaveUseDays  extends HalfIntegerPrimitiveValue<SpecialLeaveUseDays> {

	private static final long serialVersionUID = 1L;
	
	public SpecialLeaveUseDays(Double rawValue) {
		super(rawValue);
	}

}

