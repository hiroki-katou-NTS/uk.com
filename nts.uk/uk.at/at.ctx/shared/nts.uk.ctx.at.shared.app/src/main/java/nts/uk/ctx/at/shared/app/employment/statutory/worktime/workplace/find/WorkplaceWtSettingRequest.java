/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.workplace.find;

import lombok.Data;

/**
 * The Class WorkplaceWtSettingRequest.
 */
@Data
public class WorkplaceWtSettingRequest {

	/** The year. */
	private int year;

	/** The workplace id. */
	private String workplaceId;
}
