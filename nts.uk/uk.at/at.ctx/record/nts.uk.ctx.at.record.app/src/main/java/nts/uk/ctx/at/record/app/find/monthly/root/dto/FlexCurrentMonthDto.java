package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 当月フレックス時間 */
public class FlexCurrentMonthDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス時間 */
	private int flexTime;

	/** 基準時間 */
	private int standardTime;

	/** 週平均超過時間 */
	private int excessWeekAveTime;

	public FlexTimeCurrentMonth toDomain() {
		return FlexTimeCurrentMonth.of(new AttendanceTimeMonthWithMinus(flexTime),
									new AttendanceTimeMonth(standardTime),
									new AttendanceTimeMonth(excessWeekAveTime));
	}
	
	public static FlexCurrentMonthDto from(FlexTimeCurrentMonth domain) {
		FlexCurrentMonthDto dto = new FlexCurrentMonthDto();
		if(domain != null) {
			dto.setFlexTime(domain.getFlexTime().valueAsMinutes());
			dto.setStandardTime(domain.getStandardTime().valueAsMinutes());
			dto.setExcessWeekAveTime(domain.getExcessWeekAveTime().valueAsMinutes());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case FLEX:
			return Optional.of(ItemValue.builder().value(flexTime).valueType(ValueType.TIME));
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
		case FLEX:
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
		case FLEX:
			flexTime = value.valueOrDefault(0);
			break;
		case STANDARD:
			standardTime = value.valueOrDefault(0);
			break;
		case EXCESS + AVERAGE:
			excessWeekAveTime = value.valueOrDefault(0);
			break;
		default:
		}
	}
}
