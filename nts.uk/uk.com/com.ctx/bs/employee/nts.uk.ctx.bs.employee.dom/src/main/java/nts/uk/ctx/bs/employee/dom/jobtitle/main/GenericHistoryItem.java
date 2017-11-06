package nts.uk.ctx.bs.employee.dom.jobtitle.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Getter
public class GenericHistoryItem {
	
	private String historyId;
	
	private DatePeriod period;

}
