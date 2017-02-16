package nts.uk.ctx.basic.app.find.organization.position;

import java.time.LocalDate;

import lombok.Data;
import nts.uk.ctx.basic.dom.organization.position.Position;



@Data

public class JobTitleDto {
	private String historyID;
	private String jobCode;
	private String jobName;
	private String jobOutCode;
	private LocalDate startDate;
	private LocalDate endDate;
	private String memo;
	
	

	public static JobTitleDto fromDomain(Position domain) {
		JobTitleDto jobTitleDto = new JobTitleDto();
		domain.getCompanyCode();
		domain.getJobCode().v();
		domain.getHistoryID();
		domain.getMemo().v();
		domain.getJobOutCode().v();
		domain.getEndDate().localDate();
		domain.getStartDate().localDate();
		return jobTitleDto;
	};
				
				
}

