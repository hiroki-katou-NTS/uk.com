/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DeleteShainStatWorkTimeSetCommand.
 */

@Setter
@Getter
public class DeleteShainStatWorkTimeSetCommand {

	/** The year. */
	private Integer year;
	
	/** The employee id. */
	private String employeeId;
	
	/** The is over one year. */
	private boolean isOverOneYear;

}
