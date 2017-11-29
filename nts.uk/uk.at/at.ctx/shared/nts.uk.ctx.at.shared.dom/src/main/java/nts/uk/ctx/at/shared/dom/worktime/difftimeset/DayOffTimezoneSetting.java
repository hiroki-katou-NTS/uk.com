/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.HolidayWorkTimeSheetSet;

/**
 * Checks if is update start time.
 *
 * @return true, if is update start time
 */
@Getter
// 時差勤務休出時間の時間帯設定
public class DayOffTimezoneSetting extends HolidayWorkTimeSheetSet {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
}
