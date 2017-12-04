/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class GraceTimeSettingDto.
 */
@Getter
@Setter
public class GraceTimeSettingDto {

	/** The include working hour. */
	private boolean includeWorkingHour;
	
	/** The grace time. */
	private Integer graceTime;
}
