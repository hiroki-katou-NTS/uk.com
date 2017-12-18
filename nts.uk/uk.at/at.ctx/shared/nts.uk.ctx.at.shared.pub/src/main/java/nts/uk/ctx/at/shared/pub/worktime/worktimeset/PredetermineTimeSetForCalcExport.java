/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ShShortChildCareFrameExport.
 */
@Getter
@Builder
// 計算用所定時間設定
public class PredetermineTimeSetForCalcExport extends DomainObject {

	// 時間帯
	private List<TimezoneUseExport> timezones;

	// 所定時間
	private PredetermineTimeExport predTime;

	// 午前開始時刻
	private TimeWithDayAttr morningStartTime;

	// 午後開始時刻
	private TimeWithDayAttr afternoonStartTime;

	// 夜勤区分
	private boolean nightShift;

	// 残業を含めた所定時間を設定する
	private boolean predTimeIncludeOvertime;

	// 日付開始時間
	private TimeWithDayAttr startDate;

	// 1日の計算範囲
	private int oneDayCalRange;
}
