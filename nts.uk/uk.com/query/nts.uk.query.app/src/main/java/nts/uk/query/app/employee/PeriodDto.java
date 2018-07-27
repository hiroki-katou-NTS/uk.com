/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class PeriodDto.
 */
@Data
public class PeriodDto {

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;
}
