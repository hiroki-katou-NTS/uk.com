package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 残業枠時間 */
@Data
public class OverTimeFrameTimeDto {

	/** 振替時間: 計算付き時間*/
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto transferTime;
	/** 残業時間: 計算付き時間*/
	@AttendanceItemLayout(layout="B")
	private CalcAttachTimeDto overtime;
	/** 事前申請時間: 勤怠時間*/
	@AttendanceItemLayout(layout="C")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int beforeApplicationTime;
	/** 残業枠NO: 残業枠NO*/
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int overtimeFrameNo;
}
