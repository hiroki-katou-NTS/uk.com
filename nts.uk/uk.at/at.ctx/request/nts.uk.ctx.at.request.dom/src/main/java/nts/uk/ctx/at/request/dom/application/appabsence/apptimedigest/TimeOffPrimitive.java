package nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;

/**
 * @author loivt
 * 時間休
 */
@TimeRange(min = "00:00", max = "48:00")
public class TimeOffPrimitive extends TimeDurationPrimitiveValue<OvertimeAppPrimitiveTime> {

	private static final long serialVersionUID = 1L;

	public TimeOffPrimitive(Integer rawValue) {
		super(rawValue);
	}

}
