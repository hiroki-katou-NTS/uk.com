/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CoreTimeSettingDto.
 */
@Getter
@Setter
public class CoreTimeSettingDto {

	/** The core time sheet. */
	private TimeSheetDto coreTimeSheet;
	
	/** The timesheet. */
	private Integer timesheet;
	
	/** The min work time. */
	private Integer minWorkTime;
}
