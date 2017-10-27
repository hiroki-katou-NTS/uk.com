/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.employment;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmploymentEstablishmentDeleteCommand.
 */
@Getter
@Setter
public class EmploymentEstablishmentDeleteCommand {
	
	/** The target year. */
	private int targetYear;
	
	/** The employment code. */
	private String employmentCode;
	
}
