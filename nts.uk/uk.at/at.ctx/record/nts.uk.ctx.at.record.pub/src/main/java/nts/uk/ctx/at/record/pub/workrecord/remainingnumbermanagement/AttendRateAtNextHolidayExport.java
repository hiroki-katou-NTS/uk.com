package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;

/**
 * @author sonnlb 
 * 次回年休付与時点出勤率
 */
@Value
@AllArgsConstructor
public class AttendRateAtNextHolidayExport {

	/** 次回年休付与日 */
	private GeneralDate nextHolidayGrantDate;

	/** 次回年休付与日数 */
	private GrantDays nextHolidayGrantDays;

	/** 出勤率 */
	private AttendanceRate attendanceRate;

	/** 出勤日数 */
	private AttendanceDaysMonth attendanceDays;

	/** 所定日数 */
	private AttendanceDaysMonth predeterminedDays;

	/** 年間所定日数 */
	private AttendanceDaysMonth annualPerYearDays;
}
