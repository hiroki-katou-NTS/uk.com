/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ClosurePeriodDto.
 */
@Getter
@Setter
@Builder
public class ClosurePeriodDto {

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;
}
