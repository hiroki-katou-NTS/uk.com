/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;

@Builder 
public class WorkTimeDivisionDto {
	
	/** The work time daily atr. */
	public Integer workTimeDailyAtr;

	/** The work time method set. */
	public Integer workTimeMethodSet;
	
	public static WorkTimeDivisionDto fromDomain(WorkTimeDivision domain) {
	    return new WorkTimeDivisionDto(domain.getWorkTimeDailyAtr().value, domain.getWorkTimeMethodSet().value);
	}
}
