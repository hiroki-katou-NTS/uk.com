package nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage;

import java.util.List;

import nts.arc.time.GeneralDate;import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


public interface AnnualBreakManagePub {
	/**
	 * RequestList 304
	 * アルゴリズム「社員ID、期間をもとに期間内に年休付与日がある社員を抽出する」を実行する
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<AnnualBreakManageExport> getEmployeeId(List<String> employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * RequestList 327
	 * 指定年月日時点の年休残数を取得
	 * @param employeeId
	 * @param confirmDay
	 * @param holidayGrantStart
	 * @param holidayGrantEnd
	 * @return
	 */
	List<YearlyHolidaysTimeRemainingExport> getYearHolidayTimeAnnualRemaining(String employeeId, GeneralDate confirmDay, GeneralDate holidayGrantStart, GeneralDate holidayGrantEnd);

	/**
	 * 次回年休付与を計算
	 * @param employeeId
	 * @param time
	 * @return
	 */
	List<NextAnnualLeaveGrant> calculateNextHolidayGrant(String employeeId, DatePeriod time);
	
	/**
	 * 締め開始日以前に付与された年休残数を取得する
	 * @param employeeId
	 * @param companyId
	 * @param startDate
	 * @param calculateStartDate
	 * @param calculateEndDate
	 * @return
	 */
	List<YearlyHolidaysTimeRemainingExport> getNumberOfAnnualHolidayGrantedBeforeCloseDate(String employeeId, String companyId, GeneralDate startDate, GeneralDate calcStartDate, GeneralDate calcEndDate);
}
