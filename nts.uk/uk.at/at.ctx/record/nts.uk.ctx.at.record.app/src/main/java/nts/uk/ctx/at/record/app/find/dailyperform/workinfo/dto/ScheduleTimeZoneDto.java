package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 予定時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeZoneDto implements ItemConst {

	/** 勤務NO */
	private int no;

	/** 出勤 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ATTENDANCE)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private int working;

	/** 退勤 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LEAVE)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private int leave;

	@Override
	protected ScheduleTimeZoneDto clone() {
		return new ScheduleTimeZoneDto(no, working, leave);
	}
}
