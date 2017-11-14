/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Gets the employee name.
 *
 * @return the employee name
 */
@Getter
@Builder
@Setter
public class PersonInfoWorkFixedDto {
	
	/** The employee id. */
	private String employeeId;

	/** The employee name. */
	private String employeeName;

	public PersonInfoWorkFixedDto() {
		super();
	}

	public PersonInfoWorkFixedDto(String employeeId, String employeeName) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}
	
	
}
