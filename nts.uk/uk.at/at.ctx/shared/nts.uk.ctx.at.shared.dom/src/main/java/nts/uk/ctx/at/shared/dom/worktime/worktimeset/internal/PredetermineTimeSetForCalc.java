/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ShShortChildCareFrameExport.
 */
@Getter
@Setter
// 計算用所定時間設定
public class PredetermineTimeSetForCalc extends DomainObject {

	// 時間帯
	private List<TimezoneUse> timezones;

	// 所定時間
	private PredetermineTime predTime;

	// 午前開始時刻
	private TimeWithDayAttr morningEndTime;

	// 午後開始時刻
	private TimeWithDayAttr afternoonStartTime;

	// 夜勤区分
	private boolean nightShift;

	// 残業を含めた所定時間を設定する
	private boolean predTimeIncludeOvertime;

	// 日付開始時間
	private TimeWithDayAttr startDateClock;

	// 1日の計算範囲
	private AttendanceTime oneDayCalRange;
}
