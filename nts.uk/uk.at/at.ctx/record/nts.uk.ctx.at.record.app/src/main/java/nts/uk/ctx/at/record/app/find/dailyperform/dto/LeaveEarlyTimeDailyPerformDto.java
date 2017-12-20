package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の早退時間 */
@Data
public class LeaveEarlyTimeDailyPerformDto {

	/** 早退時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName="早退時間")
	private CalcAttachTimeDto leaveEarlyTime;

	/** 早退控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName="早退控除時間")
	@AttendanceItemValue(itemId = {606,612}, type = ValueType.INTEGER)
	private Integer LeaveEarlyDeductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName="時間休暇使用時間")
	private ValicationUseDto valicationUseTime;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer intervalExemptionTime;

	/** 勤務NO/勤務回数: 勤務NO */
//	@AttendanceItemLayout(layout = "E")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workTimes;
}
