package nts.uk.ctx.at.shared.dom.common.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠月間時間
 * @author shuichi_ishida
 */
@TimeRange(min = "0:00", max = "999:59")
public class AttendanceTimeMonth extends TimeDurationPrimitiveValue<AttendanceTimeMonth> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param minutes 分（0:00からの経過）
	 */
	public AttendanceTimeMonth(int minutes){
		
		super(minutes);
	}
}
