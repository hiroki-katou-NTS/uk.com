package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.AggregateTotalTimeSpentAtWork;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の総拘束時間 */
public class AggregateTotalTimeSpentAtWorkDto implements ItemConst, AttendanceItemDataGate {

	/** 拘束残業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	private int overTime;

	/** 拘束深夜時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LATE_NIGHT, layout = LAYOUT_B)
	private int midnightTime;

	/** 拘束休出時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	private int holidayTime;

	/** 拘束差異時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DIFF, layout = LAYOUT_D)
	private int varienceTime;

	/** 総拘束時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_E)
	private int totalTime;

	public AggregateTotalTimeSpentAtWork toDomain() {
		return AggregateTotalTimeSpentAtWork.of(new AttendanceTimeMonth(overTime),
												new AttendanceTimeMonth(midnightTime),
												new AttendanceTimeMonth(holidayTime),
												new AttendanceTimeMonth(varienceTime),
												new AttendanceTimeMonth(totalTime));
	}
	
	public static AggregateTotalTimeSpentAtWorkDto from(AggregateTotalTimeSpentAtWork domain) {
		AggregateTotalTimeSpentAtWorkDto dto = new AggregateTotalTimeSpentAtWorkDto();
		if(domain != null) {
			dto.setHolidayTime(domain.getHolidayTimeSpentAtWork() == null ? 0 : domain.getHolidayTimeSpentAtWork().valueAsMinutes());
			dto.setMidnightTime(domain.getMidnightTimeSpentAtWork() == null ? 0 : domain.getMidnightTimeSpentAtWork().valueAsMinutes());
			dto.setOverTime(domain.getOverTimeSpentAtWork() == null ? 0 : domain.getOverTimeSpentAtWork().valueAsMinutes());
			dto.setTotalTime(domain.getTotalTimeSpentAtWork() == null ? 0 : domain.getTotalTimeSpentAtWork().valueAsMinutes());
			dto.setVarienceTime(domain.getVarienceTimeSpentAtWork() == null ? 0 : domain.getVarienceTimeSpentAtWork().valueAsMinutes());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case OVERTIME:
			return Optional.of(ItemValue.builder().value(overTime).valueType(ValueType.TIME));
		case LATE_NIGHT:
			return Optional.of(ItemValue.builder().value(midnightTime).valueType(ValueType.TIME));
		case HOLIDAY_WORK:
			return Optional.of(ItemValue.builder().value(holidayTime).valueType(ValueType.TIME));
		case DIFF:
			return Optional.of(ItemValue.builder().value(varienceTime).valueType(ValueType.TIME));
		case TOTAL:
			return Optional.of(ItemValue.builder().value(totalTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case OVERTIME:
		case LATE_NIGHT:
		case HOLIDAY_WORK:
		case DIFF:
		case TOTAL:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case OVERTIME:
			overTime = value.valueOrDefault(0);
			break;
		case LATE_NIGHT:
			midnightTime = value.valueOrDefault(0);
			break;
		case HOLIDAY_WORK:
			holidayTime = value.valueOrDefault(0);
			break;
		case DIFF:
			varienceTime = value.valueOrDefault(0);
			break;
		case TOTAL:
			totalTime = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

	
}
