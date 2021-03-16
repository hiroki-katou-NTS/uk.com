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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の特定日数 + 集計特定日数 */
public class AggregateSpecificDaysDto implements ItemConst, AttendanceItemDataGate {

	/** 特定日項目No */
	private int no;

	/** 特定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double specificDays;

	/** 休出特定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_B)
	private double holidayWorkSpecificDays;

	public AggregateSpecificDays toDomain() {
		return AggregateSpecificDays.of(new SpecificDateItemNo(no), new AttendanceDaysMonth(specificDays),
				new AttendanceDaysMonth(holidayWorkSpecificDays));
	}

	public static AggregateSpecificDaysDto from(AggregateSpecificDays domain) {
		AggregateSpecificDaysDto dto = new AggregateSpecificDaysDto();
		if (domain != null) {
			dto.setHolidayWorkSpecificDays(
					domain.getHolidayWorkSpecificDays() == null ? 0 : domain.getHolidayWorkSpecificDays().v());
			dto.setNo(domain.getSpecificDayItemNo().v());
			dto.setSpecificDays(domain.getSpecificDays() == null ? 0 : domain.getSpecificDays().v());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(specificDays).valueType(ValueType.DAYS));
		case HOLIDAY_WORK:
			return Optional.of(ItemValue.builder().value(holidayWorkSpecificDays).valueType(ValueType.DAYS));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case HOLIDAY_WORK:
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
			specificDays = value.valueOrDefault(0d); break;
		case HOLIDAY_WORK:
			holidayWorkSpecificDays = value.valueOrDefault(0d); break;
		default:
			break;
		}
	}

	
}
