package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

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
	private Integer working;

	/** 退勤 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LEAVE)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer leave;

	@Override
	protected ScheduleTimeZoneDto clone() {
		return new ScheduleTimeZoneDto(no, working, leave);
	}
}
