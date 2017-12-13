package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の早退時間 */
@Data
public class LeaveEarlyTimeDailyPerformDto {

	/** 休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout="A")
	private ValicationUseDto valicationUseTime;
	/** 早退控除時間: 勤怠時間*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int LeaveEarlyDeductionTime;
	/** 早退時間: 計算付き時間*/
	@AttendanceItemLayout(layout="C")
	private CalcAttachTimeDto leaveEarlyTime;
	/** 勤務NO: 勤務NO*/
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workTimes;
	/** インターバル免除時間: インターバル免除時間*/
	@AttendanceItemLayout(layout="E")
	private IntervalExemptionTimeDto intervalExemptionTime;
}
