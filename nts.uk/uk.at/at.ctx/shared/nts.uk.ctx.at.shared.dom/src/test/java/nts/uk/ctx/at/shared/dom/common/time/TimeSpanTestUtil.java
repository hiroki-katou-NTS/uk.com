package nts.uk.ctx.at.shared.dom.common.time;

import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeSpanTestUtil {

	public static TimeSpanForCalc create(int start, int end) {
		return new TimeSpanForCalc(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
	}
}
