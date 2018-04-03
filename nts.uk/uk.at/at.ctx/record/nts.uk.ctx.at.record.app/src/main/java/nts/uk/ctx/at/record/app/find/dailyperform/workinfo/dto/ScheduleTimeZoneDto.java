package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 予定時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeZoneDto {

	/** 勤務NO */
	private Integer workNo;

	/** 出勤 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "出勤")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer working;

	/** 退勤 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "退勤")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer leave;
}
