/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workrule.closure;

import lombok.Builder;
import lombok.Data;

/**
 * The Class DatePeriodDto.
 */
@Data
@Builder
public class DatePeriodDto {

	/** The start date. */
	private String startDate;

	/** The end date. */
	private String endDate;
}
