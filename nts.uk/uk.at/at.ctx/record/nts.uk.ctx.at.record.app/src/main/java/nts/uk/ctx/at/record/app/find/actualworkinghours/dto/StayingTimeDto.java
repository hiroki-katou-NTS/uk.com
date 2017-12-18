package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の滞在時間 */
@Data
public class StayingTimeDto {

	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer stayingTime;

	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer beforePCLogOnTime;

	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer afterPCLogOffTime;

	@AttendanceItemLayout(layout = "D")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer beforeWoringTime;

	@AttendanceItemLayout(layout = "E")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer afterLeaveTime;
}
