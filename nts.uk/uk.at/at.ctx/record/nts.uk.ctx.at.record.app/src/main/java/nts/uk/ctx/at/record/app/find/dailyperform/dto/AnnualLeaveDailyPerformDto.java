package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の年休 */
@Data
public class AnnualLeaveDailyPerformDto {

	/** 時間消化休暇使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "間消化休暇使用時間")
	@AttendanceItemValue(itemId = 540, type = ValueType.INTEGER)
	private Integer timeDigestionVacationUseTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "使用時間")
	@AttendanceItemValue(itemId = 539, type = ValueType.INTEGER)
	private Integer annualLeaveUseTime;
}
