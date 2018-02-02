package nts.uk.ctx.at.request.dom.application.holidayworktime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * @author loivt
 * 時刻(日区分付き)
 */
@TimeRange(min = "-12:00", max = "71:59")
public class HolidayAppPrimitiveTime extends TimeDurationPrimitiveValue<HolidayAppPrimitiveTime> {

	private static final long serialVersionUID = 1L;
	
	public HolidayAppPrimitiveTime(Integer rawValue) {
		super(rawValue);
	}

}
