package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 代休時間
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class TimeDayoffRemain extends TimeDurationPrimitiveValue<TimeDayoffRemain>{

	public TimeDayoffRemain(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
