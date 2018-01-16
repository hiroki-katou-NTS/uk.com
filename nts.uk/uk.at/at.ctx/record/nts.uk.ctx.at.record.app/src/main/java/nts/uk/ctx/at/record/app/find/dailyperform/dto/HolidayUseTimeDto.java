package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の特別休暇 / 日別実績の年休 / 日別実績の超過有休 / 日別実績の代休*/
@Data
public class HolidayUseTimeDto {

	/** 時間消化休暇使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間消化休暇使用時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer timeDigestionVacationUseTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "使用時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer useTime;
}
