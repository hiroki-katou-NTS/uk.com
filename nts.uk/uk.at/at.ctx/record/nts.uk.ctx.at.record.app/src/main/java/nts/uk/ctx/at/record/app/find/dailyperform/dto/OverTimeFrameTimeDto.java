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

/** 残業枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeFrameTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 振替時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TRANSFER)
	private CalcAttachTimeDto transferTime;

	/** 残業時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TIME)
	private CalcAttachTimeDto overtime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;

	/** 指示時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer orderTime;

	/** 残業枠NO: 残業枠NO */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int no;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (TRANSFER.equals(path) || path.equals(TIME)) {
			return new CalcAttachTimeDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TRANSFER:
			return Optional.ofNullable(transferTime);
		case TIME:
			return Optional.ofNullable(overtime);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TRANSFER:
			transferTime = (CalcAttachTimeDto)  value;
			break;
		case TIME:
			overtime = (CalcAttachTimeDto)  value;
			break;
		default:
			break;
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
	public OverTimeFrameTimeDto clone() {
		return new OverTimeFrameTimeDto(transferTime == null ? null : transferTime.clone(), 
				overtime == null ? null : overtime.clone(), beforeApplicationTime, orderTime, no);
	}
}
