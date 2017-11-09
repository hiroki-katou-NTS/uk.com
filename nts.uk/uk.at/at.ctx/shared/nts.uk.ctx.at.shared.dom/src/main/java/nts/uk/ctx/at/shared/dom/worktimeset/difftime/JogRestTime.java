/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import java.util.List;

import lombok.Getter;

/**
 * The Class BreakTime.
 */
@Getter
// 時差勤務の休憩時間帯
public class JogRestTime {

	/** The setting. */
	// 休憩時間帯
	private List<JogDeductionTime> setting;
}
