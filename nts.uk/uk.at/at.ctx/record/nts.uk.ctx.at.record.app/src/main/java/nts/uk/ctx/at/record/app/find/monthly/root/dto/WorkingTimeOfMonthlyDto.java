package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.WorkTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の就業時間 */
public class WorkingTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 就業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WORK_TIME, layout = LAYOUT_A)
	private int workTime;

	/** 所定内割増時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = PREMIUM, layout = LAYOUT_B)
	private int withinPrescribedPremiumTime;
	
	/** 実働就業時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ACTUAL, layout = LAYOUT_C)
	private int actualWorkTime;
	
	public static WorkingTimeOfMonthlyDto from(WorkTimeOfMonthly domain) {
		WorkingTimeOfMonthlyDto dto = new WorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setWithinPrescribedPremiumTime(domain.getWithinPrescribedPremiumTime() == null 
					? 0 : domain.getWithinPrescribedPremiumTime().valueAsMinutes());
			dto.setWorkTime(domain.getWorkTime() == null ? 0 : domain.getWorkTime().valueAsMinutes());
			dto.setActualWorkTime(domain.getActualWorkTime() == null ? 0 : domain.getActualWorkTime().valueAsMinutes());
		}
		return dto;
	}
	
	public WorkTimeOfMonthly toDomain() {
		return WorkTimeOfMonthly.of(new AttendanceTimeMonth(workTime),
									new AttendanceTimeMonth(withinPrescribedPremiumTime),
									new AttendanceTimeMonth(actualWorkTime));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORK_TIME:
			return Optional.of(ItemValue.builder().value(workTime).valueType(ValueType.TIME));
		case PREMIUM:
			return Optional.of(ItemValue.builder().value(withinPrescribedPremiumTime).valueType(ValueType.TIME));
		case ACTUAL:
			return Optional.of(ItemValue.builder().value(actualWorkTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORK_TIME:
		case PREMIUM:
		case ACTUAL:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORK_TIME:
			workTime = value.valueOrDefault(0);
			break;
		case PREMIUM:
			withinPrescribedPremiumTime = value.valueOrDefault(0);
			break;
		case ACTUAL:
			actualWorkTime = value.valueOrDefault(0);
			break;
		default:
		}
	}	
}
