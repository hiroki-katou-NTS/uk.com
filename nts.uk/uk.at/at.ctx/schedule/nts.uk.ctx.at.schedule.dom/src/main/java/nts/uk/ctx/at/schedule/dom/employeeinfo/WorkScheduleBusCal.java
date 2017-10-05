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
	// 就業時間帯の参照先
	private WorkScheduleMasterReferenceAtr referenceBasicWork;
	
	/** The working hours. */
	// 就業時間帯の参照先
	private TimeZoneScheduledMasterAtr referenceWorkingHours;
	
	
	
	
}
