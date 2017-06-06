/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import lombok.AllArgsConstructor;

/**
 * The Enum WeekStart.
 */
@AllArgsConstructor
public enum WeekStart {

	/** The Monday. */
	Monday(0),

	/** The Tuesday. */
	Tuesday(1),

	/** The Wednesday. */
	Wednesday(2),

	/** The Thursday. */
	Thursday(3),

	/** The Friday. */
	Friday(4),

	/** The Saturday. */
	Saturday(5),

	/** The Sunday. */
	Sunday(6),

	/** The Tightening start date. */
	TighteningStartDate(7);

	/** The value. */
	public final int value;
}
