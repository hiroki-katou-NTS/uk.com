package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement;

import java.util.List;
import java.util.Optional;

/**
 * @author sonnlb
 *
 */
public interface AnnualHolidayManagementAdapter {
	/**
	 * RequestList210 
	 * 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	List<NextAnnualLeaveGrantImport> acquireNextHolidayGrantDate(String cId, String sId);

	/**
	 * RequestList323 
	 * 次回年休付与時点の出勤率・出勤日数・所定日数・年間所定日数を取得する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */

	public Optional<AttendRateAtNextHolidayImport> getDaysPerYear(String companyId, String employeeId);
}
