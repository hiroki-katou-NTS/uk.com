package nts.uk.ctx.at.shared.dom.common.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠年間時間
 * @author shuichi_ishida
 */
@TimeRange(min = "0:00", max = "9999:59")
public class AttendanceTimeYear extends TimeDurationPrimitiveValue<AttendanceTimeYear> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param minutes 分（0:00からの経過）
	 */
	public AttendanceTimeYear(int minutes){
		super(minutes);
	}
	
	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 9999 * 60 + 59) rawValue = 9999 * 60 + 59;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
