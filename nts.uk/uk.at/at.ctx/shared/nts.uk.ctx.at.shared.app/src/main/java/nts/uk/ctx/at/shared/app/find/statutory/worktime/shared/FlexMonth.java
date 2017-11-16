/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import lombok.Data;

/**
 * The Class FlexMonth.
 */
@Data
public class FlexMonth {

	/** The month. */
	private int month;

	/** The statutory time. */
	private int statutoryTime;

	/** The specified time. */
	private int specifiedTime;
}
