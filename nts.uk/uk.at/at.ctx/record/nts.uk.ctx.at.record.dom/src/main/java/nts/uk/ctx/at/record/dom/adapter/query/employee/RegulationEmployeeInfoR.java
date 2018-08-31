package nts.uk.ctx.at.record.dom.adapter.query.employee;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Builder
@Data
public class RegulationEmployeeInfoR {
	
	/** The employee id. */
	private String employeeId; 

	/** The employee code. */
	private GeneralDate targetDate; 
	
	private String errorAlarmID;
	/** The workplace name. */
	private String businessTypeCode;
	
	private String employmentCode;
	
	private String jobTitleId;
	
	private String classificationCode;
}
