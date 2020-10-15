package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class WorkScheduleTimeZoneImport {
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
