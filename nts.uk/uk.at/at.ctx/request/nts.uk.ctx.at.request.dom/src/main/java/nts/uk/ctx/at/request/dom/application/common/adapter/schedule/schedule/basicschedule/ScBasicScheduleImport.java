package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ScBasicScheduleImport {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	/** The work type code. */
	// 勤務種類
	private String workTypeCode;

	/** The work time code. */
	// 就業時間帯
	private String workTimeCode;
	
	/** The schedule start clock. */
	// 予定開始時刻1
	private int scheduleStartClock1;

	/** The schedule end clock. */
	// 予定終了時刻1
	private int scheduleEndClock1;
	
	/** The schedule start clock. */
	// 予定開始時刻2
	private int scheduleStartClock2;

	/** The schedule end clock. */
	// 予定終了時刻2
	private int scheduleEndClock2;
}
