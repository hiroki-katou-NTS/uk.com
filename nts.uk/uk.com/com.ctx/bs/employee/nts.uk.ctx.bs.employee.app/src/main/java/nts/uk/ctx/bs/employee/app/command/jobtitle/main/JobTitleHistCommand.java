package nts.uk.ctx.bs.employee.app.command.jobtitle.main;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class JobTitleHistCommand {

	private String sid;
	/** The job title id. */
	//職位ID
	private String jobTitleId;
	
	private String historyId;
	
	/** The job title history. */
	private GeneralDate startDate;
	
	private GeneralDate endDate;

}
