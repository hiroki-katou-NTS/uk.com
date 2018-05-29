package nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 必要時間数
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class RequiredTime extends TimeDurationPrimitiveValue<RequiredTime>{

	public RequiredTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
