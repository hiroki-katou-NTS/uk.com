package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/**
 * 特別休暇使用時間
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeaveUseTimesDto implements ItemConst, AttendanceItemDataGate {

	/**
	 * 使用時間
	 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.TIME)
	private int useTimes;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TIME:
			return Optional.of(ItemValue.builder().value(useTimes).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TIME:
			useTimes = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {

		switch (path) {
		case TIME:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

}
