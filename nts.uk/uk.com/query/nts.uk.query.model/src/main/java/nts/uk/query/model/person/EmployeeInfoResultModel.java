/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import java.util.List;

import lombok.Data;

/**
 * The Class EmployeeInfoModel.
 */
@Data
public class EmployeeInfoResultModel {
	
	/** The employee id. */
	private String employeeId;
	
	/** The employment historys. */
	private List<EmploymentHistoryModel> employmentHistorys;
	
	/** The work place historys. */
	private List<WorkPlaceHistoryModel> workPlaceHistorys;
	
	/** The classification historys. */
	private List<ClassificationHistoryModel> classificationHistorys;
	
	/** The business type historys. */
	private List<BusinessTypeHistoryModel> businessTypeHistorys;
	
	private List<JobTitleHistoryModel> jobTitleHistorys;
}
