/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;

/**
 * Checks if is update start time.
 *
 * @return true, if is update start time
 */
@Getter
// 時差勤務休出時間の時間帯設定
public class DayOffTimezoneSetting {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
}
