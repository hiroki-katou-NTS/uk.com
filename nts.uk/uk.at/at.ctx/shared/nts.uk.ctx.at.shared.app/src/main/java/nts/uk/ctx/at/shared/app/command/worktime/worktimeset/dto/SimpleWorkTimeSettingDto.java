/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto;

import lombok.Builder;

/**
 * The Class SimpleWorktimeSettingDto.
 */
@Builder
public class SimpleWorkTimeSettingDto {
	
	/** The company id. */
	public String companyId;

	/** The worktime code. */
	public String worktimeCode;
	
	/** The work time name. */
	public String workTimeName;
}
