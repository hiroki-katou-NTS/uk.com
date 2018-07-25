package nts.uk.ctx.at.shared.dom.common.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠年間時間
 * @author shuichu_ishida
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
}
