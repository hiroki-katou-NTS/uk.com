package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class DateHistoryItem extends GeneralHistoryItem<DateHistoryItem, DatePeriod, GeneralDate> {

	public DateHistoryItem(String historyId, DatePeriod span) {
		super(historyId, span);
	}

}
