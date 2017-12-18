package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の所定内時間 */
@Data
public class WithinStatutoryTimeDailyPerformDto {

	/** 就業時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workTime;

	/** 実働就業時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workTimeIncludeVacationTime;

	/** 所定内割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer withinPrescribedPremiumTime;

	/** 所定内深夜時間: 所定内深夜時間 */
	@AttendanceItemLayout(layout = "D")
	private CalcAttachTimeDto withinStatutoryMidNightTime;

	/** 休暇加算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "E")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer vacationAddTime;
}
