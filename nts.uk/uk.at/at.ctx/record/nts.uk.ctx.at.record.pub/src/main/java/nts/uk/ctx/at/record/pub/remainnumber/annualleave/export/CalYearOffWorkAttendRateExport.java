package nts.uk.ctx.at.record.pub.remainnumber.annualleave.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalYearOffWorkAttendRateExport {
	
	/**
	 * 出勤率
	 */
	private Double attendanceRate;
	
	/**
	 * 所定日数
	 */
	private Double prescribedDays;
	
	/**
	 * 労働日数
	 */
	private Double workingDays;
	
	/**
	 * 控除日数
	 */
	private Double deductedDays;

}
