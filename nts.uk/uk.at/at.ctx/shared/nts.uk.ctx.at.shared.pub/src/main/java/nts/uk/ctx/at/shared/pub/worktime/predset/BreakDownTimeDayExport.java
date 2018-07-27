/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.predset;

import lombok.Builder;
import lombok.Data;

/**
 * The Class BreakDownTimeDayExport.
 */
@Data
@Builder
public class BreakDownTimeDayExport {
	/** The one day. */
	// 1日
	private Integer oneDay;

	/** The morning. */
	// 午前
	private Integer morning;

	/** The afternoon. */
	// 午後
	private Integer afternoon;
}
