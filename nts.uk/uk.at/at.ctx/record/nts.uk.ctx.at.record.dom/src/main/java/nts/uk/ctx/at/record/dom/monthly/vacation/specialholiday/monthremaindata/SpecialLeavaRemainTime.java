package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 特別休暇残時間
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class SpecialLeavaRemainTime extends TimeDurationPrimitiveValue<SpecialLeavaRemainTime>{

	public SpecialLeavaRemainTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
