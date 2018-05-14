/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.employee.dto;

import lombok.Value;

/**
 * The Class PersonContactDto.
 */
@Value
public class PersonContactDto {

	/** The person id. */
	private String personId;

	/** The mail address. */
	private String mailAddress;

	/** The mobile mail address. */
	private String mobileMailAddress;

	/** The cell phone no. */
	private String cellPhoneNo;

}
