package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
/** 集計休業日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateLeaveDaysDto implements ItemConst, AttendanceItemDataGate {

	/** 休業区分: 休業区分 */
//	@AttendanceItemValue(type = ValueType.INTEGER)
//	@AttendanceItemLayout(jpPropertyName = "休業区分", layout = "A", needCheckIDWithMethod = "leaveAtr")
	private int attr;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private double days;

	@Override
	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_OFF_BEFORE_BIRTH;
		case 1:
			return E_OFF_AFTER_BIRTH;
		case 2:
			return E_OFF_CHILD_CARE;
		case 3:
			return E_OFF_CARE;
		case 4:
			return E_OFF_INJURY;
		default:
			return EMPTY_STRING;
		}
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (DAYS.equals(path)) {
			return Optional.of(ItemValue.builder().value(days).valueType(ValueType.DAYS));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (DAYS.equals(path)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if (DAYS.equals(path)) {
			days = value.valueOrDefault(0d);
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_OFF_BEFORE_BIRTH:
			this.attr = 0; break;
		case E_OFF_AFTER_BIRTH:
			this.attr = 1; break;
		case E_OFF_CHILD_CARE:
			this.attr = 2; break;
		case E_OFF_CARE:
			this.attr = 3; break;
		case E_OFF_INJURY:
			this.attr = 4; break;
		default:
		}
	}
}
