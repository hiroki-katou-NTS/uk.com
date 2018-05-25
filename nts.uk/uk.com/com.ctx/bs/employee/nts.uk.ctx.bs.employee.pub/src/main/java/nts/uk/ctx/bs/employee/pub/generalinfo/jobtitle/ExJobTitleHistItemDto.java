package nts.uk.ctx.bs.employee.pub.generalinfo.jobtitle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
public class ExJobTitleHistItemDto {
	
	private String historyId;
	
	private DatePeriod period;
	
	private String jobTitleId;
	
}
