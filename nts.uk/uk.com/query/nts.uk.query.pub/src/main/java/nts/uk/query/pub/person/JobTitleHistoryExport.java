/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.person.JobTitleHistoryModel;

/**
 * The Class JobTitleHistoryModel.
 */
public class JobTitleHistoryExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The job id. */
	public String jobId;
	
	/** The start date. */
	public GeneralDateTime startDate;
	
	/** The end date. */
	public GeneralDateTime endDate;
	
	/**
	 * Instantiates a new job title history export.
	 *
	 * @param res the res
	 */
	public JobTitleHistoryExport(JobTitleHistoryModel res) {
		this.employeeId = res.getEmployeeId();
		this.jobId = res.getJobId();
		this.startDate = res.getStartDate();
		this.endDate = res.getEndDate();
	}
}
