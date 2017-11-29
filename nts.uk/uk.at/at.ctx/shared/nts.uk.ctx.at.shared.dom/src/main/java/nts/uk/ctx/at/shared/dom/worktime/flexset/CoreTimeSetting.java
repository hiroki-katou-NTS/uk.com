/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;

/**
 * The Class CoreTimeSetting.
 */
@Getter
//コアタイム時間帯設定
public class CoreTimeSetting extends DomainObject {

	/** The core time sheet. */
	// コアタイム時間帯
	private TimeSheet coreTimeSheet;
	
	/** The timesheet. */
	// 使用区分
	private ApplyAtr timesheet;
	
	/** The min work time. */
	// 最低勤務時間
	private AttendanceTime minWorkTime;

}
