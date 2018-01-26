/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import lombok.Builder;

/**
 * The Class SimpleWorktimeSettingDto.
 */
@Builder
public class SimpleWorkTimeSettingDto {
	
	/** The worktime code. */
	public String worktimeCode;
	
	/** The work time name. */
	public String workTimeName;

	/** The is abolish. */
	public boolean isAbolish;
}
