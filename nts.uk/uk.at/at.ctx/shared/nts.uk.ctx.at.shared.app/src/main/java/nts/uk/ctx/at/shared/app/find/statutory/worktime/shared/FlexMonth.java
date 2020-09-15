/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class FlexMonth.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlexMonth {

	/** The month. */
	private int month;

	/** The statutory time. */
	private int statutoryTime;

	/** The specified time. */
	private int specifiedTime;

	/** The specified time. */
	private int weekAvgTime;
}
