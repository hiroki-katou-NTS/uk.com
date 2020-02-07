package nts.uk.ctx.at.record.dom.jobtitle.affiliate;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class AffJobTitleSidImport {
	
	private String employeeId;
	
	private String jobTitleId;
	
	private DatePeriod dateRange;

	public AffJobTitleSidImport(String employeeId, String jobTitleId, DatePeriod dateRange) {
		super();
		this.employeeId = employeeId;
		this.jobTitleId = jobTitleId;
		this.dateRange = dateRange;
	}

}
