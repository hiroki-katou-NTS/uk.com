package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * ３６協定１ヶ月時間
 * @author yennth
 *
 */
@TimeRange(min = "00:00", max = "744:00")
public class OverTime extends TimeDurationPrimitiveValue<OverTime>{

	public OverTime(int rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
