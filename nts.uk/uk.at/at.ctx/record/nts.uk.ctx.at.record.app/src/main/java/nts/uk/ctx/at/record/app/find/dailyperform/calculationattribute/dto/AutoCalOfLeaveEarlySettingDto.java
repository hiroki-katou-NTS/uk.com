package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

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
@AllArgsConstructor
@NoArgsConstructor
/** 遅刻早退の自動計算設定 */
public class AutoCalOfLeaveEarlySettingDto implements ItemConst, AttendanceItemDataGate {

	/** 早退: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LEAVE_EARLY)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int leaveEarly;

	/** 遅刻: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int leaveLate;

	@Override
	protected AutoCalOfLeaveEarlySettingDto clone() {
		return new AutoCalOfLeaveEarlySettingDto(leaveEarly, leaveLate);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case LEAVE_EARLY:
			return Optional.of(ItemValue.builder().value(leaveEarly).valueType(ValueType.ATTR));
		case LATE:
			return Optional.of(ItemValue.builder().value(leaveLate).valueType(ValueType.ATTR));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case LEAVE_EARLY:
			this.leaveEarly = value.valueOrDefault(0);
			break;
		case LATE:
			this.leaveLate = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case LEAVE_EARLY:
		case LATE:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
}
