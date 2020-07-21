/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;

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
	
	/**
	 * 出勤時の勤務情報を取得する (new_2020)
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param baseDate 年月日
	 * @param workTypeCode 勤務種類コード
	 * @param workingDayCategory  稼働日区分
	 * @return
	 */
	Optional<WorkInformation> getHolidayWorkScheduleNew(String companyId, String employeeId, GeneralDate baseDate, String workTypeCode,
			WorkingDayCategory workingDayCategory);

}
