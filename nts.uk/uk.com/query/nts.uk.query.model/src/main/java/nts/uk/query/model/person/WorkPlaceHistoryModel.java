/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * The Class WorkPlaceHistoryModel.
 */
@Data
@Builder
public class WorkPlaceHistoryModel {
	
	/** The employee id. */
	private String employeeId;
	
	/** The workplace id. */
	private String workplaceId;
	
	/** The start date. */
	private GeneralDateTime startDate;
	
	/** The end date. */
	private GeneralDateTime endDate;
}
