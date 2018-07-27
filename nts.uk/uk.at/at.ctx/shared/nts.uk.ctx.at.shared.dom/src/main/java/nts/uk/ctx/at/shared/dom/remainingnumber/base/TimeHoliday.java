package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 時間休
 * @author shuichu_ishida
 */
@TimeRange(min = "0:00", max = "48:00")
public class TimeHoliday extends TimeDurationPrimitiveValue<TimeHoliday> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param minutes 分　（0:00からの経過）
	 */
	public TimeHoliday(int minutes){
		super(minutes);
	}
}
