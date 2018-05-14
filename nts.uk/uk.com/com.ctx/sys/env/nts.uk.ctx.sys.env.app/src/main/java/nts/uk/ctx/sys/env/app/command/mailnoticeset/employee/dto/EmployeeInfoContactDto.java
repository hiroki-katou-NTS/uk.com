/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.employee.dto;

import lombok.Value;

/**
 * The Class EmployeeInfoContactDto.
 */
@Value
public class EmployeeInfoContactDto {

	/** The employee id. */
	private String employeeId;

	/** The mail address. */
	private String mailAddress;

	/** The mobile mail address. */
	private String mobileMailAddress;

	/** The cell phone no. */
	private String cellPhoneNo;

}
