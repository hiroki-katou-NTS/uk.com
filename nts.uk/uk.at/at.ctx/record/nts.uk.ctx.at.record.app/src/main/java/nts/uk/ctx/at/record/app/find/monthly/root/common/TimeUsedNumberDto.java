package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseTimes;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 時間年休使用時間 */
/** 年休上限残時間 */
public class TimeUsedNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 使用回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	private int usedTimes;

	/** 使用時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int usedTime;

	/** 使用時間付与前 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_C)
	private int usedTimeBeforeGrant;

	/** 使用時間付与後 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_D)
	private Integer usedTimeAfterGrant;

	public static TimeUsedNumberDto from(UsedMinutes domain) {
		return domain == null ? null : new TimeUsedNumberDto(
						0,
						domain.valueAsMinutes(),
						0,
						null);
	}

	public UsedMinutes toDomain() {
		return new UsedMinutes(usedTime);
	}



	public static TimeUsedNumberDto from(SpecialLeaveUseTimes domain) {

//		return domain == null ? null : new TimeUsedNumberDto(
//						domain.getUseNumber().v(),
//						domain.getUseTimes().valueAsMinutes(),
//						domain.getBeforeUseGrantTimes().valueAsMinutes(),
//						domain.getAfterUseGrantTimes().isPresent() ? domain.getAfterUseGrantTimes().get().valueAsMinutes() : null);
//		return null;
		return domain == null ? null : new TimeUsedNumberDto(
				0,
				domain.getUseTimes().valueAsMinutes(),
				0,
				null);
	}

	public SpecialLeaveUseTimes toSpecial(){
		return new SpecialLeaveUseTimes(new SpecialLeavaRemainTime(usedTime));

	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(usedTimes).valueType(ValueType.COUNT));
		case TIME:
			return Optional.of(ItemValue.builder().value(usedTime).valueType(ValueType.TIME));
		case (GRANT + BEFORE):
			return Optional.of(ItemValue.builder().value(usedTimeBeforeGrant).valueType(ValueType.TIME));
		case (GRANT + AFTER):
			return Optional.of(ItemValue.builder().value(usedTimeAfterGrant).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
		case TIME:
		case (GRANT + BEFORE):
		case (GRANT + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			usedTimes = value.valueOrDefault(0); break;
		case TIME:
			usedTime = value.valueOrDefault(0); break;
		case (GRANT + BEFORE):
			usedTimeBeforeGrant = value.valueOrDefault(0); break;
		case (GRANT + AFTER):
			usedTimeAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}

	
}
