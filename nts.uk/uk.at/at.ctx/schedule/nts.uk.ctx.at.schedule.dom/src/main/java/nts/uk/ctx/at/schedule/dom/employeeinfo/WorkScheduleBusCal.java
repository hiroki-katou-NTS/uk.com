/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkScheduleBusCal.
 */
// 営業日カレンダーによる勤務予定作成
@Getter
public class WorkScheduleBusCal extends DomainObject{

	/** The reference business day calendar. */
	// 営業日カレンダーの参照先
	private WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar;
	
	/** The reference basic work. */
	// 基本勤務の参照先
	private WorkScheduleMasterReferenceAtr referenceBasicWork;
	
	/** The working hours. */
	// 就業時間帯の参照先
	private TimeZoneScheduledMasterAtr referenceWorkingHours;

	/**
	 * Instantiates a new work schedule bus cal.
	 *
	 * @param referenceBusinessDayCalendar the reference business day calendar
	 * @param referenceBasicWork the reference basic work
	 * @param referenceWorkingHours the reference working hours
	 */
	public WorkScheduleBusCal(WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar,
			WorkScheduleMasterReferenceAtr referenceBasicWork,
			TimeZoneScheduledMasterAtr referenceWorkingHours) {
		this.referenceBusinessDayCalendar = referenceBusinessDayCalendar;
		this.referenceBasicWork = referenceBasicWork;
		this.referenceWorkingHours = referenceWorkingHours;
	}
		
}
