/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import nts.arc.time.GeneralDate;
import nts.uk.query.model.person.BusinessTypeHistoryModel;

/**
 * The Class BusinessTypeHistoryModel.
 */
public class BusinessTypeHistoryExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The business type code. */
	public String businessTypeCode;
	
	/** The start date. */
	public GeneralDate startDate;
	
	/** The end date. */
	public GeneralDate endDate;
	
	/**
	 * Instantiates a new employment history export.
	 *
	 * @param res the res
	 */
	public BusinessTypeHistoryExport(BusinessTypeHistoryModel res) {
		this.employeeId = res.getEmployeeId();
		this.businessTypeCode = res.getBusinessTypeCode();
		this.startDate = res.getStartDate();
		this.endDate = res.getEndDate();
	}
}
