/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class PredetermineTime.
 */
@Getter
@Builder
public class PredetermineTimeExport  {

	/** The add time. */
	//就業加算時間
	private BreakDownTimeDayExport addTime;
	
	/** The pred time. */
	//所定時間
	private BreakDownTimeDayExport predTime;
	
	
}
