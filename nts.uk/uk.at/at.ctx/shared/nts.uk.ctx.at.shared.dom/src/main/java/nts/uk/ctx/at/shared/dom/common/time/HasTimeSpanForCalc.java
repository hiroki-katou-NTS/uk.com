package nts.uk.ctx.at.shared.dom.common.time;

import nts.uk.shr.com.time.TimeWithDayAttr;

public interface HasTimeSpanForCalc<T> {

	TimeSpanForCalc getTimeSpan();
	
	T newSpanWith(TimeWithDayAttr start, TimeWithDayAttr end);
}
