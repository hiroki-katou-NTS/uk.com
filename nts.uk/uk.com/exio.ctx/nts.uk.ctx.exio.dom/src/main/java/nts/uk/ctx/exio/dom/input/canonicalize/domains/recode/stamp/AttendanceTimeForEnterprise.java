package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 勤怠時間(Enterprise)
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"-1"として保持しているため
 *
 */
@TimeRange(max = "48:00", min = "-00:01")
public class AttendanceTimeForEnterprise extends TimeDurationPrimitiveValue<AttendanceTimeForEnterprise> {

	public static final AttendanceTimeForEnterprise ZERO = new AttendanceTimeForEnterprise(0);

	private static final long serialVersionUID = 1L;

	public AttendanceTimeForEnterprise(Integer rawValue) {
		super(rawValue);
	}

	public boolean isEmpty(){
		// マイナス値の場合はempty扱い
		return this.v() < 0;
	}
}
