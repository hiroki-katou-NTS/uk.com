/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import lombok.Builder;
import lombok.Getter;

/**
 * Gets the employee name.
 *
 * @return the employee name
 */
@Getter

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
public class PersonInfoWorkFixedDto {
	
	/** The employee id. */
	private String employeeId;

	/** The employee name. */
	private String employeeName;
}
