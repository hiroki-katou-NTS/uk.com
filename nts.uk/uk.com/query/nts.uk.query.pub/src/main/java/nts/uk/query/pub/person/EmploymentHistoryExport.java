/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.person.EmploymentHistoryModel;

/**
 * The Class EmploymentHistoryModel.
 */
public class EmploymentHistoryExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The employment code. */
	public String employmentCode;
	
	/** The start date. */
	public GeneralDateTime startDate;
	
	/** The end date. */
	public GeneralDateTime endDate;
	
	/**
	 * Instantiates a new employment history export.
	 *
	 * @param res the res
	 */
	public EmploymentHistoryExport(EmploymentHistoryModel res) {
		this.employeeId = res.getEmployeeId();
		this.employmentCode = res.getEmploymentCode();
		this.startDate = res.getStartDate();
		this.endDate = res.getEndDate();
	}
}
