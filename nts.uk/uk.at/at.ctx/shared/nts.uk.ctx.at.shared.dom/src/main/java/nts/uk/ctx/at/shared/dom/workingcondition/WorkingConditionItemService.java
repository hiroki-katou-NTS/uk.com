/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkingConditionItemService.
 */
public interface WorkingConditionItemService {

	/**
	 * Gets the holiday work schedule.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @param workTypeCode the work type code
	 * @return the holiday work schedule
	 */
	// 休日出勤時の勤務情報を取得する (Lấy 休日出勤時の勤務情報)
	Optional<SingleDaySchedule> getHolidayWorkSchedule(String companyId, String employeeId, GeneralDate baseDate, String workTypeCode);

}
