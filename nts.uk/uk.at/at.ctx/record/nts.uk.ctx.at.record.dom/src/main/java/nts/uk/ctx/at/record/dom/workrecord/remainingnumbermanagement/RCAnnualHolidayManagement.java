package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

public interface RCAnnualHolidayManagement {
	/**
	 * RequestList210
	 * 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	List<NextAnnualLeaveGrant> acquireNextHolidayGrantDate(CompanyId cId, EmployeeId sId, Optional<GeneralDate> referenceDate);

	/**
	 * RequestList323 
	 * 次回年休付与時点の出勤率・出勤日数・所定日数・年間所定日数を取得する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */

	public Optional<AttendRateAtNextHoliday> getDaysPerYear(CompanyId cId, EmployeeId sId);

}
