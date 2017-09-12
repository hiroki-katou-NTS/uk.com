/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

import lombok.AllArgsConstructor;

/**
 * The Enum PerformanceClassification.
 */
// 任意項目利用区分
@AllArgsConstructor
public enum PerformanceClassification {

	/** The monthly performance. */
	// 月別実績
	MONTHLY_PERFORMANCE(0),

	/** The daily performance. */
	// 日別実績
	DAILY_PERFORMANCE(1);

	/** The value. */
	public final int value;
}
