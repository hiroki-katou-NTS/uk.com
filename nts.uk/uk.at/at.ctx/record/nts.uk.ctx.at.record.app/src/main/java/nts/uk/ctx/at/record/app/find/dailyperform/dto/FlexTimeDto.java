package nts.uk.ctx.at.record.app.find.dailyperform.dto;

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

/** フレックス時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTimeDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間: 計算付き時間(マイナス有り) */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto flexTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (TIME.equals(path)) {
			return new CalcAttachTimeDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (TIME.equals(path)) {
			return Optional.ofNullable(flexTime);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (TIME.equals(path)) {
			flexTime = (CalcAttachTimeDto) value;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (path.equals(BEFOR_APPLICATION)) {
			return Optional.of(ItemValue.builder().value(beforeApplicationTime).valueType(ValueType.TIME));
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(BEFOR_APPLICATION)) {
			this.beforeApplicationTime = value.valueOrDefault(null);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(BEFOR_APPLICATION)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
	
	@Override
	public FlexTimeDto clone() {
		return new FlexTimeDto(flexTime == null ? null : flexTime.clone(), beforeApplicationTime);
	}
}
