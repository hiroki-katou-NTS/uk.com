package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AnnualHolidayManagementPub {
	/**
	 * RequestList210
	 * 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	List<NextAnnualLeaveGrantExport> acquireNextHolidayGrantDate(String cId, String sId, Optional<GeneralDate> referenceDate);

	/**
	 * RequestList323 
	 * 次回年休付与時点の出勤率・出勤日数・所定日数・年間所定日数を取得する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */

	public Optional<AttendRateAtNextHolidayExport> getDaysPerYear(String companyId, String employeeId);

}
