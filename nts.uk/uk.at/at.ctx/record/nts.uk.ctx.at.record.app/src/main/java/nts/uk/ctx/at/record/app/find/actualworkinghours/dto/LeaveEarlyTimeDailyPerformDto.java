package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の早退時間 */
@Data
public class LeaveEarlyTimeDailyPerformDto {
	
	/** 勤務NO/勤務回数: 勤務NO*/
//	@AttendanceItemLayout(layout="D")
//	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workTimes;
	
	/** 早退時間: 計算付き時間*/
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto leaveEarlyTime;
	
	/** 早退控除時間: 勤怠時間*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int LeaveEarlyDeductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout="C")
	private ValicationUseDto valicationUseTime;
	
	/** インターバル免除時間/インターバル時間: インターバル免除時間*/
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer intervalExemptionTime;
}
