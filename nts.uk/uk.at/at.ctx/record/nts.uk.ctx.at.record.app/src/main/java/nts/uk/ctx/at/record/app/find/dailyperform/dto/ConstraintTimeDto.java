package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ValueType;

/** 総拘束時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintTimeDto {

	/** 総拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "総拘束時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer totalConstraintTime;

	/** 深夜拘束時間 : 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "深夜拘束時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer lateNightConstraintTime;
}
