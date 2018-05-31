/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.person.WorkPlaceHistoryModel;

/**
 * The Class WorkPlaceHistoryModel.
 */
public class WorkPlaceHistoryExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The workplace id. */
	public String workplaceId;
	
	/** The start date. */
	public GeneralDateTime startDate;
	
	/** The end date. */
	public GeneralDateTime endDate;
	
	/**
	 * Instantiates a new work place history export.
	 *
	 * @param res the res
	 */
	public WorkPlaceHistoryExport(WorkPlaceHistoryModel res) {
		this.employeeId = res.getEmployeeId();
		this.workplaceId = res.getWorkplaceId();
		this.startDate = res.getStartDate();
		this.endDate = res.getEndDate();
	}
}
