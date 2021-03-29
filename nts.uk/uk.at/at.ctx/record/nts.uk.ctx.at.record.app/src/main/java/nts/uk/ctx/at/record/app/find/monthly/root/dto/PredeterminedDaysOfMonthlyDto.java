package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.PredeterminedDaysOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の所定日数 */
public class PredeterminedDaysOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 所定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double predeterminedDays;

	/** 所定日数付与前: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private double predeterminedDaysBeforeGrant;

	/** 所定日数付与後: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private double predeterminedDaysAfterGrant;

	public PredeterminedDaysOfMonthly toDomain() {
		return PredeterminedDaysOfMonthly.of(
						new AttendanceDaysMonth(predeterminedDays));//,
//						predeterminedDaysBeforeGrant == null ? null : new AttendanceDaysMonth(predeterminedDaysBeforeGrant), 
//						predeterminedDaysAfterGrant == null ? null : new AttendanceDaysMonth(predeterminedDaysAfterGrant));
	}
	
	public static PredeterminedDaysOfMonthlyDto from(PredeterminedDaysOfMonthly domain) {
		PredeterminedDaysOfMonthlyDto dto = new PredeterminedDaysOfMonthlyDto();
		if(domain != null) {
			dto.setPredeterminedDays(domain.getPredeterminedDays() == null 
					? 0 : domain.getPredeterminedDays().v());
//			dto.setPredeterminedDaysAfterGrant(domain.getPredeterminedDaysAfterGrant() == null 
//					? null : domain.getPredeterminedDaysAfterGrant().v());
//			dto.setPredeterminedDaysBeforeGrant(domain.getPredeterminedDaysBeforeGrant() == null 
//					? null : domain.getPredeterminedDaysBeforeGrant().v());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(predeterminedDays).valueType(ValueType.DAYS));
		case BEFORE:
			return Optional.of(ItemValue.builder().value(predeterminedDaysBeforeGrant).valueType(ValueType.DAYS));
		case AFTER:
			return Optional.of(ItemValue.builder().value(predeterminedDaysAfterGrant).valueType(ValueType.DAYS));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case BEFORE:
		case AFTER:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			predeterminedDays = value.valueOrDefault(0d); break;
		case BEFORE:
			predeterminedDaysBeforeGrant = value.valueOrDefault(0d); break;
		case AFTER:
			predeterminedDaysAfterGrant = value.valueOrDefault(0d); break;
		default:
			break;
		}
	}
}
