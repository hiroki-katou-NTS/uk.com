/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.predset;

import lombok.Builder;
import lombok.Data;

/**
 * The Class PredeterminedTimeExport.
 */
@Data
@Builder
public class PredeterminedTimeExport {
	
	/** The add time. */
	// 就業加算時間
	private BreakDownTimeDayExport addTime;

	/** The pred time. */
	// 所定時間
	private BreakDownTimeDayExport predTime;

}
