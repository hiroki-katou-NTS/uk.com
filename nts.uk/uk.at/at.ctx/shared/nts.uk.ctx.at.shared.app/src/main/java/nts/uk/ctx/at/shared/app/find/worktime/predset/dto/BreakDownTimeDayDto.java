/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.Builder;

/**
 * The Class BreakDownTimeDayDto.
 */
@Builder
public class BreakDownTimeDayDto {
	/** The one day. */
	public Integer oneDay;

	/** The morning. */
	public Integer morning;

	/** The afternoon. */
	public Integer afternoon;
}
