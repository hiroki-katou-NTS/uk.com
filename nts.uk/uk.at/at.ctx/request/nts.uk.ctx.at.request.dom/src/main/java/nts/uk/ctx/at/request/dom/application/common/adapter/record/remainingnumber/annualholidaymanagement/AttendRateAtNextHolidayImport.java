package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport.AttendanceDaysMonthImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport.AttendanceRateImport;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;

/**
 * @author sonnlb
 *
 */
@Value
@AllArgsConstructor
public class AttendRateAtNextHolidayImport {
	
	/** 次回年休付与日 */
	private GeneralDate nextHolidayGrantDate;

	/** 次回年休付与日数 */
	private GrantDays nextHolidayGrantDays;

	/** 出勤率 */
	private AttendanceRateImport attendanceRate;

	/** 出勤日数 */
	private AttendanceDaysMonthImport attendanceDays;

	/** 所定日数 */
	private AttendanceDaysMonthImport predeterminedDays;

	/** 年間所定日数 */
	private AttendanceDaysMonthImport annualPerYearDays;
	
	
}
