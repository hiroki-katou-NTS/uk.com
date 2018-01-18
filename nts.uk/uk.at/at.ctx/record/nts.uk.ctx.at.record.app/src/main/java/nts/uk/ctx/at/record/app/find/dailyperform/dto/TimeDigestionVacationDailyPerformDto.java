package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の時間消化休暇 */
@Data
public class TimeDigestionVacationDailyPerformDto {

	/** 不足時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "不足時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortageTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "使用時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer useTime;
}
