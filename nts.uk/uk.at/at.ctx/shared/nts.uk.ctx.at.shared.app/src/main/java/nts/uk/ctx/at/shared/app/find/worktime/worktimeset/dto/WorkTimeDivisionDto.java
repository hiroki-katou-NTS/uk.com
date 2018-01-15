/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import lombok.Builder;

@Builder 
public class WorkTimeDivisionDto {
	
	/** The work time daily atr. */
	public Integer workTimeDailyAtr;

	/** The work time method set. */
	public Integer workTimeMethodSet;
}
