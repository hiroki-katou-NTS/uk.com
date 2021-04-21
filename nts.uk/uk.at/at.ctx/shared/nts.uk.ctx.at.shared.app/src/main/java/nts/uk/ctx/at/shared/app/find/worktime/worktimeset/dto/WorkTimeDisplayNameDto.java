/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;

@Builder
public class WorkTimeDisplayNameDto {
	
	/** The work time name. */
	public String workTimeName;
	
	/** The work time ab name. */
	public String workTimeAbName;
	
	/** The work time symbol. */
	public String workTimeSymbol;
	

    
    public static WorkTimeDisplayNameDto fromDomain(WorkTimeDisplayName domain) {
        return new WorkTimeDisplayNameDto(domain.getWorkTimeName() == null ? null : domain.getWorkTimeName().v(), 
                domain.getWorkTimeAbName() == null ? null : domain.getWorkTimeAbName().v(), 
                domain.getWorkTimeSymbol() == null ? null : domain.getWorkTimeSymbol().v());
    }
}
