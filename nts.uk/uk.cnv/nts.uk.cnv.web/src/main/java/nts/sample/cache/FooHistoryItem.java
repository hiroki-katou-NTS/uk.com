package nts.sample.cache;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class FooHistoryItem {

	private String employeeId;
	private DatePeriod period;
}
