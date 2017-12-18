package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 総拘束時間 */
@Data
public class ConstraintTimeDto {

	/** 総拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer totalConstraintTime;

	/** 深夜拘束時間 : 勤怠時間 */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer lateNightConstraintTime;
}
