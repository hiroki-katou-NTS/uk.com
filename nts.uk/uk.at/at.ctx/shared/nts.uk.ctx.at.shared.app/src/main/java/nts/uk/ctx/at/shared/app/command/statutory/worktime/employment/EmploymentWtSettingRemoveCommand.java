/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employment;

import lombok.Data;

/**
 * The Class CompanySettingRemoveCommand.
 */
@Data
public class EmploymentWtSettingRemoveCommand {

	/** The year. */
	private int year;

	/** The employment code. */
	private String employmentCode;
}
