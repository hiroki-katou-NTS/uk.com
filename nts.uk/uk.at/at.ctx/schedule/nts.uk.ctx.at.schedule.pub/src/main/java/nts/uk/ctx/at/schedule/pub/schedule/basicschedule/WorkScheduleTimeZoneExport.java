/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkScheduleTimeZoneExport.
 */
@Getter
@Setter
public class WorkScheduleTimeZoneExport {
	/** The schedule cnt. */
	// 予定勤務回数
	private int scheduleCnt;
	
	/** The schedule start clock. */
	// 予定開始時刻
	private int scheduleStartClock;

	/** The schedule end clock. */
	// 予定終了時刻
	private int scheduleEndClock;
	
	/** The bounce atr. */
	// 直行直帰区分
	private int bounceAtr;
}
