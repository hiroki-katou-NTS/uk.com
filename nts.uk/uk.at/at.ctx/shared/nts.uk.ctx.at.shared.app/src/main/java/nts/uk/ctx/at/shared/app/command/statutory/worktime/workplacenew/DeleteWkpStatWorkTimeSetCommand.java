/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DeleteWkpStatWorkTimeSetCommand.
 */
@Setter
@Getter
public class DeleteWkpStatWorkTimeSetCommand {

	/** The year. */
	private Integer year;
	
	/** The employee id. */
	private String workplaceId;
	
	/** The is over one year. */
	private boolean isOverOneYear;

}
