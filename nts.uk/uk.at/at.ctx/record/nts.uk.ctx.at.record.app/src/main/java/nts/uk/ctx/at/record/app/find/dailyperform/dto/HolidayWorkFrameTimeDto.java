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

/** 休出枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkFrameTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 休出時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto holidayWorkTime;

	/** 振替時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TRANSFER)
	private CalcAttachTimeDto transferTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BEFOR_APPLICATION)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer beforeApplicationTime;

	/** 休出枠NO: 休出枠NO */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue( type = ValueType.INTEGER)
	private int no;
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TIME:
			return Optional.ofNullable(holidayWorkTime);
		case TRANSFER:
			return Optional.ofNullable(transferTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TIME:
			holidayWorkTime = (CalcAttachTimeDto) value;
			break;
		case TRANSFER:
			transferTime = (CalcAttachTimeDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TIME:
		case TRANSFER:
			return new CalcAttachTimeDto();
		default:
			return null;
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
			this.beforeApplicationTime = value.valueOrDefault(0);
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
	public HolidayWorkFrameTimeDto clone() {
		return new HolidayWorkFrameTimeDto(holidayWorkTime == null ? null : holidayWorkTime.clone(), 
											transferTime == null ? null : transferTime.clone(),
											beforeApplicationTime, no);
	}
}
