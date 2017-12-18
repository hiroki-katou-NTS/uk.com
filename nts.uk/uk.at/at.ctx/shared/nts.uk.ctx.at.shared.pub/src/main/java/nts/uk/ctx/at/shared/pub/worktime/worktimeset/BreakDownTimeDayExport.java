/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class BreakDownTimeDay.
 */
@Builder
@Getter
// １日の時間内訳
public class BreakDownTimeDayExport {

	/** The one day. */
	// 1日
	private int oneDay;

	/** The morning. */
	// 午前
	private int morning;

	/** The afternoon. */
	// 午後
	private int afternoon;

}
