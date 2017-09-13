package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 勤怠打刻
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkStamp {
	private TimeWithDayAttr timesOfDay;
}
