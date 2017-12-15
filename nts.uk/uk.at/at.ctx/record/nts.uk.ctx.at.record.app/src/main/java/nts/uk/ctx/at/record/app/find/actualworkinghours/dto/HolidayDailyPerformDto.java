package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の休暇 */
@Data
public class HolidayDailyPerformDto {

	/** 年休: 日別実績の年休 */
	@AttendanceItemLayout(layout="A")
	private AnnualLeaveDailyPerformDto annualLeave;
	
	/** 特別休暇: 日別実績の特別休暇 */
	@AttendanceItemLayout(layout="B")
	private SpecialHolidayDailyPerformDto specialHoliday;
	
	/** 超過有休: 日別実績の超過有休 */
	@AttendanceItemLayout(layout="C")
	private ExcessSalariesDailyPerformDto excessSalaries;
	
	/** 代休: 日別実績の代休 */
	@AttendanceItemLayout(layout="D")
	private CompensatoryLeaveDailyPerformDto compensatoryLeave;
	
	/** 積立年休: 日別実績の積立年休 */
	@AttendanceItemLayout(layout="E")
	private RetentionYearlyDailyPerformDto RetentionYearly;
	
	/** 時間消化休暇: 日別実績の時間消化休暇 */
	@AttendanceItemLayout(layout="F")
	private TimeDigestionVacationDailyPerformDto timeDigestionVacation;
	
	/** 欠勤: 日別実績の欠勤 */
	@AttendanceItemLayout(layout="G")
	private AbsenceDailyPerformDto absence;
}
