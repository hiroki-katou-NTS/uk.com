/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ScBasicScheduleExport.
 */
@Getter
@Setter
@NoArgsConstructor
public class ScBasicScheduleExport {
	
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
	private List<WorkScheduleTimeZoneExport> workScheduleTimeZones;
	
	public ScBasicScheduleExport(String employeeId, GeneralDate date, String workTypeCode, String workTimeCode,
			List<WorkScheduleTimeZoneExport> workScheduleTimeZones) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.workScheduleTimeZones = workScheduleTimeZones;
	}
	
	
	
}
