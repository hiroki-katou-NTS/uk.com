package nts.uk.ctx.basic.app.find.organization.position;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;

@Value
public class JobHistDto {

	GeneralDate startDate;
	
	GeneralDate endDate;

	String historyId;

	

	public static JobHistDto fromDomain(JobHistory domain) {
		return new JobHistDto(domain.getStartDate()				
				,domain.getEndDate()
				, domain.getHistoryId());
	}
}
