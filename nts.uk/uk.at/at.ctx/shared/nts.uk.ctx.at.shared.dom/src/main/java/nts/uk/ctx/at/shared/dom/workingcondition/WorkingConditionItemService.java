/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
	Optional<WorkInformation> getHolidayWorkSchedule(String companyId, String employeeId, GeneralDate baseDate, String workTypeCode);
	
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
	
	/**
	 * @name <<Public>> 社員（List）の労働条件を期間で取得する
	 * @param sIds ・対象社員一覧：Input「社員一覧」
	 * @param datePeriod ・対象期間：Input「基準日」 
	 * @return ・労働条件項目
	 */
	List<WorkingConditionItem> getEmployeesIdListByPeriod(List<String> sIds, DatePeriod datePeriod);

	/**
	 * 
	 * @param employeeId 社員ID
	 * @param ymd 基準日
	 * @return 就業時間帯コード
	 */
	String getWorkTimeWorkHoliday(String employeeId, GeneralDate ymd);
	
}
