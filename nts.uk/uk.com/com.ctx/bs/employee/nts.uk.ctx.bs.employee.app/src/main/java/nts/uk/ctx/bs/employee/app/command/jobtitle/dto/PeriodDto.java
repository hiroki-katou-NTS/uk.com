/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.jobtitle.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new period dto.
 */
@Data
public class PeriodDto {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
}
