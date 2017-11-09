/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime_old.DeductionTime;

/**
 * The Class JoDeductionTime.
 */
@Getter
// 時差勤務の控除時間帯
public class JogDeductionTime extends DeductionTime {

	/** The allow change according start time. */
	// 開始時刻に合わせて時刻を変動させる
	private Boolean allowChangeAccordingStartTime;
}
