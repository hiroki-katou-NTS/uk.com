/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace;

import lombok.Data;

/**
 * The Class CompanySettingRemoveCommand.
 */
@Data
public class WorkplaceWtSettingRemoveCommand {

	/** The year. */
	private int year;

	/** The workplace id. */
	private String workplaceId;
}
