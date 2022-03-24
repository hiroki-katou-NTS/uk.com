package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * E版打刻データ勤怠時間
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"-1"として保持しているため
 */
@SuppressWarnings("serial")
@TimeRange(max = "48:00", min = "-00:01")
public class EnterpriseStampDataAttendanceTime extends TimeDurationPrimitiveValue<EnterpriseStampDataAttendanceTime> {

	public static final EnterpriseStampDataAttendanceTime ZERO = new EnterpriseStampDataAttendanceTime(0);

	public EnterpriseStampDataAttendanceTime(Integer rawValue) {
		super(rawValue);
	}

	public boolean isEmpty() {
		return v() == -1;
	}
}
