/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class BreakDownTimeDayDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BreakDownTimeDayDto {
	/** The one day. */
	public Integer oneDay;

	/** The morning. */
	public Integer morning;

	/** The afternoon. */
	public Integer afternoon;
}
