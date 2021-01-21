package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule;

import java.util.List;

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
public class ScBasicScheduleImport_Old {
	
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
	
	/** The work schedule time zones. */
	// 勤務予定時間帯
	private List<WorkScheduleTimeZoneImport> workScheduleTimeZones;
}
