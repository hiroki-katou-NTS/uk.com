package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 外出時間帯 */
@Data
public class GoOutTimeDto {

	/** 戻り: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = "A")
	private ActualTimeStampDto comeBack;

	/** 外出: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = "B")
	private ActualTimeStampDto outing;

	/** 外出時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer outingTime;

	/** 外出枠NO: 外出枠NO */
	@AttendanceItemLayout(layout = "D")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer outingFrameNo;

	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout = "E")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer outingReason;
}
