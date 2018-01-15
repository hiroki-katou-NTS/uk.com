/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.personal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CompanyEstablishmentDeleteCommand.
 */
@Getter
@Setter
public class PersonalEstablishmentDeleteCommand {
	
	/** The target year. */
	private int targetYear;
	
	
	/** The employee id. */
	private String employeeId;
}
