/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import java.util.List;

/**
 * The Class EmployeeInfoModel.
 */
public class EmployeeInfoExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The employment historys. */
	public List<EmploymentHistoryExport> employmentHistorys;
	
	/** The work place historys. */
	public List<WorkPlaceHistoryExport> workPlaceHistorys;
	
	/** The classification historys. */
	public List<ClassificationHistoryExport> classificationHistorys;
	
	/** The business type historys. */
	public List<BusinessTypeHistoryExport> businessTypeHistorys;
	
	/** The job title historys. */
	public List<JobTitleHistoryExport> jobTitleHistorys;
}
