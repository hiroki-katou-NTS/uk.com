package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

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
	private Double nextHolidayGrantDays;

	/** 出勤率 */
	private BigDecimal attendanceRate;

	/** 出勤日数 */
	private BigDecimal attendanceDays;

	/** 所定日数 */
	private Double predeterminedDays;

	/** 年間所定日数 */
	private Double annualPerYearDays;
	
	
}
