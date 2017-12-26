package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan;

import lombok.Value;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class TimeSpan {
	private TimeWithDayAttr startClock;
	private TimeWithDayAttr endClock;
}
