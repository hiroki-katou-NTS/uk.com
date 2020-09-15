/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class MonthlyDto.
 */

/**
 * Instantiates a new monthly dto.
 */
@Data
@AllArgsConstructor
public class MonthlyDto {

	/** The month. */
	private int month;

	/** The time. */
	private int time;
}
