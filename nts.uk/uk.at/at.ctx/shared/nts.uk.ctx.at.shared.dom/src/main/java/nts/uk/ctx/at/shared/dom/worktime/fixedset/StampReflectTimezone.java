/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class StampReflectTimezone.
 */
//打刻反映時間帯
@Getter
public class StampReflectTimezone extends DomainObject {

	/** The work no. */
	// 勤務NO
	private WorkNo workNo;

	/** The classification. */
	// 出退勤区分
	 private GoLeavingWorkAtr classification;

	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;

	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;
}
