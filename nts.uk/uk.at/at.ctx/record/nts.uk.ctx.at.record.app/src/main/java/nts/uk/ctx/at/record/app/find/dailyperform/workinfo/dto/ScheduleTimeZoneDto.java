package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/** 予定時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeZoneDto implements ItemConst, AttendanceItemDataGate {

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

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case ATTENDANCE:
			return Optional.of(ItemValue.builder().value(working).valueType(ValueType.TIME_WITH_DAY));
		case LEAVE:
			return Optional.of(ItemValue.builder().value(leave).valueType(ValueType.TIME_WITH_DAY));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case ATTENDANCE:
			this.working = value.valueOrDefault(null);
			break;
		case LEAVE:
			this.leave = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case ATTENDANCE:
		case LEAVE:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
}
