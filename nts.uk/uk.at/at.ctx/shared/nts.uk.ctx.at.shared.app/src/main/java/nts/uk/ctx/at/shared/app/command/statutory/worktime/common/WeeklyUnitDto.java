/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class WeeklyUnitDto.
 */
@Getter
@Setter
@Builder
public class WeeklyUnitDto {

	/** The time. */
	private Integer time;

	/** The start. */
	private Integer start;

}
