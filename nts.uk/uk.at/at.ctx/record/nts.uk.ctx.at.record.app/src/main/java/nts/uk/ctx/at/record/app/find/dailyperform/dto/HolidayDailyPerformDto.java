package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

/** 日別実績の休暇 */
@Data
public class HolidayDailyPerformDto {

	/** 年休: 日別実績の年休 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "年休")
	private HolidayUseTimeDto annualLeave;

	/** 特別休暇: 日別実績の特別休暇 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "特別休暇")
	private HolidayUseTimeDto specialHoliday;

	/** 超過有休: 日別実績の超過有休 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "超過有休")
	private HolidayUseTimeDto excessSalaries;

	/** 代休: 日別実績の代休 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "代休")
	private HolidayUseTimeDto compensatoryLeave;

	/** 積立年休: 日別実績の積立年休 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "積立年休")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer retentionYearly;

	/** 時間消化休暇: 日別実績の時間消化休暇 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "時間消化休暇")
	private TimeDigestionVacationDailyPerformDto timeDigestionVacation;

	/** 欠勤: 日別実績の欠勤 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "欠勤")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer absence;
}
