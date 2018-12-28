package nts.uk.ctx.bs.employee.dom.employment.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Getter
@AllArgsConstructor
public class DateHistItem {
	private String sid;
	private String historyId;
	private DatePeriod period;
}
