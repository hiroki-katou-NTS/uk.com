package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AnnualBreakManageAdapter {
	/**
	 * RequestList 304
	 * アルゴリズム「社員ID、期間をもとに期間内に年休付与日がある社員を抽出する」を実行する
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<AnnualBreakManageImport> getEmployeeId(List<String> employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * RequestList 327 
	 * 指定年月日時点の年休残数を取得
	 * @param employeeId
	 * @param confirmDay
	 * @return
	 */
	List<YearlyHolidaysTimeRemainingImport> getYearHolidayTimeAnnualRemaining(String employeeId, GeneralDate confirmDay, GeneralDate holidayGrantStart, GeneralDate holidayGrantEnd);

	/**
	 * 次回年休付与を計算
	 * @param employeeId
	 * @param time
	 * @return
	 */
	List<NextAnnualLeaveGrant> calculateNextHolidayGrant(String employeeId, DatePeriod time);
	/**
 	 * RequestList #328
	 * @param employeeId
	 * @param workTypeList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Optional<DailyWorkTypeListImport> getDailyWorkTypeUsed(String employeeId, List<String> workTypeList, GeneralDate startDate, GeneralDate endDate);

}
