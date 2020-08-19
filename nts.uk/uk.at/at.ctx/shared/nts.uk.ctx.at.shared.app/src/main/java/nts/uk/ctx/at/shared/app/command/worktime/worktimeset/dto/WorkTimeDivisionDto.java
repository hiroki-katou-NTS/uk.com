/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkTimeDivisionDto {
	
	/** The work time daily atr. */
	public Integer workTimeDailyAtr;

	/** The work time method set. */
	public Integer workTimeMethodSet;
}
