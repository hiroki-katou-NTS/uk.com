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
 * 特別休暇使用日数
 * 
 * @author thanh_nx
 *
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SpecialLeaveUseDaysDto implements ItemConst, AttendanceItemDataGate {

	/**
	 * 使用日数
	 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.DAYS)
	private double useDays;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(useDays).valueType(ValueType.DAYS));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			useDays = value.valueOrDefault(0.0);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {

		switch (path) {
		case DAYS:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

}
