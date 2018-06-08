package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 特別休暇残数用付与日数
 * @author do_dt
 *
 */
@HalfIntegerRange(min=0d, max = 9999.9d)
public class SpecialLeaveGrantUseDay extends HalfIntegerPrimitiveValue<SpecialLeaveGrantUseDay> {

	public SpecialLeaveGrantUseDay(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
