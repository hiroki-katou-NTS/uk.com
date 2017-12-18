/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class Timezone.
 */
// 時間帯(使用区分付き)
@Getter
public class TimezoneUseExport {

	/** The use atr. */
	// 使用区分
	private int useAtr;

	/** The work no. */
	// 勤務NO
	private int workNo;

	/** The start. */
	// 開始
	protected TimeWithDayAttr start;

	/** The end. */
	// 終了
	protected TimeWithDayAttr end;

}
