package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeTotalTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 当月フレックス時間 */
public class FlexCurrentMonthDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間 */
	private FlexTotalTimeDto flexTime;

	/** 基準時間 */
	private int standardTime;

	/** 週平均超過時間 */
	private int excessWeekAveTime;

	public FlexTimeCurrentMonth toDomain() {
		return FlexTimeCurrentMonth.of(flexTime == null ? new FlexTimeTotalTimeMonth() : flexTime.domain(),
									new AttendanceTimeMonth(standardTime),
									new AttendanceTimeMonth(excessWeekAveTime));
	}
	
	public static FlexCurrentMonthDto from(FlexTimeCurrentMonth domain) {
		FlexCurrentMonthDto dto = new FlexCurrentMonthDto();
		if(domain != null) {
			dto.setFlexTime(FlexTotalTimeDto.from(domain.getFlexTime()));
			dto.setStandardTime(domain.getStandardTime().valueAsMinutes());
			dto.setExcessWeekAveTime(domain.getExcessWeekAveTime().valueAsMinutes());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case STANDARD:
			return Optional.of(ItemValue.builder().value(standardTime).valueType(ValueType.TIME));
		case EXCESS + AVERAGE:
			return Optional.of(ItemValue.builder().value(excessWeekAveTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case STANDARD:
		case EXCESS + AVERAGE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case STANDARD:
			standardTime = value.valueOrDefault(0);
			break;
		case EXCESS + AVERAGE:
			excessWeekAveTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case FLEX:
			return Optional.ofNullable(flexTime);
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case FLEX:
			flexTime = (FlexTotalTimeDto) value; break;
		default:
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case FLEX:
			return new FlexTotalTimeDto();
		default:
			return null;
		}
	}
}
