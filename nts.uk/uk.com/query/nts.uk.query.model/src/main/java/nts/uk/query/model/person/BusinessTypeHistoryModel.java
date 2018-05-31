/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class BusinessTypeHistoryModel.
 */
@Data
@Builder
public class BusinessTypeHistoryModel {
	
	/** The employee id. */
	private String employeeId;
	
	/** The business type code. */
	private String businessTypeCode;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
}
