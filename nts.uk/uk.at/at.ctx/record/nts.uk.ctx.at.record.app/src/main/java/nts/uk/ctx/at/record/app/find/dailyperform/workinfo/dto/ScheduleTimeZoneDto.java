package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 予定時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeZoneDto {

	/** 勤務NO */
	private Integer workNo;

	/** 出勤 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "出勤", needCheckIDWithIndex = true)
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer working;

	/** 退勤 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "退勤", needCheckIDWithIndex = true)
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer leave;
}
