package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
public class AffWorkPlaceSidImport {
	
	String employeeId;
	
	String workPlaceId;
	
	String workLocationCode;
	
	private DatePeriod dateRange;

	public AffWorkPlaceSidImport(String employeeId, String workPlaceId, String workLocationCode, DatePeriod dateRange) {
		super();
		this.employeeId = employeeId;
		this.workPlaceId = workPlaceId;
		this.workLocationCode = workLocationCode;
		this.dateRange = dateRange;
	}
}
