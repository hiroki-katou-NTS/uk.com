package nts.uk.ctx.basic.app.find.organization.position;

import lombok.Value;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;


@Value
public class JobTitleDto {
	
	String companyCode;
	
	String historyId;
	
	String jobCode;
		
	String jobName;
	
	int presenceCheckScopeSet;

	String jobOutCode;

	String memo;

	public static JobTitleDto fromDomain(JobTitle domain) {
		return new JobTitleDto(domain.getCompanyCode().v(),
				domain.getHistoryId().toString(), 
				domain.getJobCode().v(),domain.getJobName().v(),
				domain.getPresenceCheckScopeSet().value,
				domain.getJobOutCode().v(), domain.getMemo().v()
				);
	}
}
