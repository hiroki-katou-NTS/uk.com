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
	
	/** The schedule start clock. */
	// 予定開始時刻
	private int scheduleStartClock;

	/** The schedule end clock. */
	// 予定終了時刻
	private int scheduleEndClock;
	
}
