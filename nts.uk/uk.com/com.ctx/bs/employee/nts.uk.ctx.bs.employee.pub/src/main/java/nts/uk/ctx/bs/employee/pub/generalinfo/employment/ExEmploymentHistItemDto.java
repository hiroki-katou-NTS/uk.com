package nts.uk.ctx.bs.employee.pub.generalinfo.employment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
public class ExEmploymentHistItemDto {
	
	private String historyId;
	
	private DatePeriod period;
	
	private String employmentCode;
	
}
