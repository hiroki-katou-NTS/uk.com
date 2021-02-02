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

/** 日別実績の臨時枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryTimeFrameDto implements ItemConst, AttendanceItemDataGate {

	/** 勤務NO */
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private int no;

	/** 臨時深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE_NIGHT)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer temporaryNightTime;

	/** 臨時時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer temporaryTime;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case LATE_NIGHT:
			return Optional.of(ItemValue.builder().value(temporaryNightTime).valueType(ValueType.TIME));
		case TIME:
			return Optional.of(ItemValue.builder().value(temporaryTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case LATE_NIGHT:
		case TIME:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case LATE_NIGHT:
			this.temporaryNightTime = value.valueOrDefault(null);
			break;
		case TIME:
			this.temporaryTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public TemporaryTimeFrameDto clone() {
		return new TemporaryTimeFrameDto(no, temporaryNightTime, temporaryTime);
	}

}
