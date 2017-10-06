package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 予定時間帯
 * @author ken_takasu
 *
 */
@Value
public class ScheduleTimeSheet {
	private TimeWithDayAttr attendance;
	private TimeWithDayAttr leaveWork;
	private int workNo;
}
