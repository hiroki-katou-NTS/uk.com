/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ScheduleErrorLogGeterCommand.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleErrorLogGeterCommand {
	
	/** The execution id. */
	private String executionId;
	
	/** The company id. */
	private String companyId;
	
	/** The to date. */
	private GeneralDate toDate;

}
