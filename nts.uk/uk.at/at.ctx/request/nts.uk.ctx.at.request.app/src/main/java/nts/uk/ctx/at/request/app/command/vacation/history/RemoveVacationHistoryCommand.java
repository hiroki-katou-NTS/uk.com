/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.app.command.vacation.history;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveVacationHistoryCommand {
	
	/** The history id. */
	private String historyId;
	
	/** The work type code. */
	private String workTypeCode;
}
