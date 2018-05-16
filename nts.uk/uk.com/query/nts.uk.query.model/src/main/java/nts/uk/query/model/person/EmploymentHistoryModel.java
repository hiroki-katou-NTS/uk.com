/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * The Class EmploymentHistoryModel.
 */
@Data
@Builder
public class EmploymentHistoryModel {
	
	/** The employee id. */
	private String employeeId;
	
	/** The employment code. */
	private String employmentCode;
	
	/** The start date. */
	private GeneralDateTime startDate;
	
	/** The end date. */
	private GeneralDateTime endDate;
}
