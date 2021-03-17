package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TimeDigestOfDaily;

/** 日別実績の時間消化休暇 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeDigestionVacationDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 不足時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = SHORTAGE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer shortageTime;

	/** 使用時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = USAGE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer useTime;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case SHORTAGE:
			return Optional.of(ItemValue.builder().value(shortageTime).valueType(ValueType.TIME));
		case USAGE:
			return Optional.of(ItemValue.builder().value(useTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case SHORTAGE:
		case USAGE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case SHORTAGE:
			shortageTime = value.valueOrDefault(null);
			break;
		case USAGE:
			useTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public TimeDigestionVacationDailyPerformDto clone() {
		return new TimeDigestionVacationDailyPerformDto(shortageTime, useTime);
	}
	
	public static TimeDigestionVacationDailyPerformDto from(TimeDigestOfDaily domain) {
		return domain == null ? null: new TimeDigestionVacationDailyPerformDto(
				domain.getLeakageTime() == null ? 0 : domain.getLeakageTime().valueAsMinutes(), 
				domain.getUseTime() == null ? 0 : domain.getUseTime().valueAsMinutes());
	}
	
	public TimeDigestOfDaily toDomain() {
		return new TimeDigestOfDaily(useTime == null ? AttendanceTime.ZERO : new AttendanceTime(useTime),
									shortageTime == null ? AttendanceTime.ZERO : new AttendanceTime(shortageTime));
	}
	
	public static TimeDigestOfDaily defaultDomain() {
		return new TimeDigestOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO);
}	
}
