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
public class WorkTimeDisplayNameDto {
	
	/** The work time name. */
	public String workTimeName;
	
	/** The work time ab name. */
	public String workTimeAbName;
	
	/** The work time symbol. */
	public String workTimeSymbol;
}
