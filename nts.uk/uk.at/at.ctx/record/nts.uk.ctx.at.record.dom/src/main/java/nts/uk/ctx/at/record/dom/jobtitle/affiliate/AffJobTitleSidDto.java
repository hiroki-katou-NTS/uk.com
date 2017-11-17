package nts.uk.ctx.at.record.dom.jobtitle.affiliate;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
public class AffJobTitleSidDto {
	
	private String employeeId;
	
	private String jobTitleId;
	
	private DatePeriod dateRange;

	public AffJobTitleSidDto(String employeeId, String jobTitleId, DatePeriod dateRange) {
		super();
		this.employeeId = employeeId;
		this.jobTitleId = jobTitleId;
		this.dateRange = dateRange;
	}

}
