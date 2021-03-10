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

/** 所定外深夜時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOfStatutoryMidNightTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto time;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;
	
	@Override
	public ExcessOfStatutoryMidNightTimeDto clone() {
		return new ExcessOfStatutoryMidNightTimeDto(time == null ? null : time.clone(), beforeApplicationTime);
	}
	
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
			return Optional.ofNullable(time);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (TIME.equals(path)) {
			time = (CalcAttachTimeDto) value;
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
			beforeApplicationTime = value.valueOrDefault(null);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(BEFOR_APPLICATION)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
}
