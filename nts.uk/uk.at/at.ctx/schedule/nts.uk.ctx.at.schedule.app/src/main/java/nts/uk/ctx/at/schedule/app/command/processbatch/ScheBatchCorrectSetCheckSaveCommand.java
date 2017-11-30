/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ScheBatchCorrectSetCheckSaveCommand.
 */
@Getter
@Setter
public class ScheBatchCorrectSetCheckSaveCommand {

   /** The work type code. */
   private String worktypeCode;

    /** The employee id. */
    private String employeeId;

	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/** The work time code. */
	private String worktimeCode;
	
	/** The employee ids. */
	private List<String> employeeIds;
}
