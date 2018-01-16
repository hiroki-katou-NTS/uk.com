package nts.uk.ctx.at.request.dom.application.overtime.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * @author loivt
 * 残業申請時間
 */
@TimeRange(min = "00:00", max = "48:00")
public class OvertimeAppPrimitiveTime extends TimeDurationPrimitiveValue<OvertimeAppPrimitiveTime> {

	
	private static final long serialVersionUID = 1L;

	public OvertimeAppPrimitiveTime(Integer rawValue) {
		super(rawValue);
	}

}
