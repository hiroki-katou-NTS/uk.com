package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.RegAndIrgTimeOfWeekly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の通常変形時間 */
public class RegularAndIrregularTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 週割増合計時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WEEKLY_PREMIUM + TOTAL, layout = LAYOUT_A)
	private int weeklyTotalPremiumTime;

	/** 月割増合計時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = MONTHLY_PREMIUM + TOTAL, layout = LAYOUT_B)
	private int monthlyTotalPremiumTime;

	/** 変形労働時間 */
	@AttendanceItemLayout(jpPropertyName = IRREGULAR + LABOR, layout = LAYOUT_C)
	private IrregularWorkingTimeOfMonthlyDto irregularWorkingTime;

	public RegularAndIrregularTimeOfMonthly toDomain() {
		return RegularAndIrregularTimeOfMonthly.of(
				new AttendanceTimeMonth(weeklyTotalPremiumTime), 
				new AttendanceTimeMonth(monthlyTotalPremiumTime),
				irregularWorkingTime == null ? new IrregularWorkingTimeOfMonthly() : irregularWorkingTime.toDomain());
	}
	
	public static RegularAndIrregularTimeOfMonthlyDto from(RegularAndIrregularTimeOfMonthly domain) {
		RegularAndIrregularTimeOfMonthlyDto dto = new RegularAndIrregularTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIrregularWorkingTime(IrregularWorkingTimeOfMonthlyDto.from(domain.getIrregularWorkingTime()));
			dto.setMonthlyTotalPremiumTime(domain.getMonthlyTotalPremiumTime() == null 
					? 0 : domain.getMonthlyTotalPremiumTime().valueAsMinutes());
			dto.setWeeklyTotalPremiumTime(domain.getWeeklyTotalPremiumTime() == null 
					? 0 : domain.getWeeklyTotalPremiumTime().valueAsMinutes());
		}
		return dto;
	}
	
	public static RegularAndIrregularTimeOfMonthlyDto from(RegAndIrgTimeOfWeekly domain) {
		RegularAndIrregularTimeOfMonthlyDto dto = new RegularAndIrregularTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIrregularWorkingTime(new IrregularWorkingTimeOfMonthlyDto(0, 0, 0, 
											domain.getIrregularLegalOverTime().v(), 0, 0));
			dto.setWeeklyTotalPremiumTime(domain.getWeeklyTotalPremiumTime().valueAsMinutes());
		}
		return dto;
	}

	public RegAndIrgTimeOfWeekly toDomainWeekly() {
		
		val irgLegalOt = this.irregularWorkingTime == null ? 0 : this.irregularWorkingTime.getIrregularLegalOverTime();
		
		return RegAndIrgTimeOfWeekly.of(new AttendanceTimeMonth(weeklyTotalPremiumTime), new AttendanceTimeMonth(irgLegalOt));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (WEEKLY_PREMIUM + TOTAL):
			return Optional.of(ItemValue.builder().value(weeklyTotalPremiumTime).valueType(ValueType.TIME));
		case (MONTHLY_PREMIUM + TOTAL):
			return Optional.of(ItemValue.builder().value(monthlyTotalPremiumTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if ((IRREGULAR + LABOR).equals(path)) {
			return new IrregularWorkingTimeOfMonthlyDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if ((IRREGULAR + LABOR).equals(path)) {
			return Optional.ofNullable(irregularWorkingTime);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (WEEKLY_PREMIUM + TOTAL):
		case (MONTHLY_PREMIUM + TOTAL):
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (WEEKLY_PREMIUM + TOTAL):
			weeklyTotalPremiumTime = value.valueOrDefault(0);
			break;
		case (MONTHLY_PREMIUM + TOTAL):
			monthlyTotalPremiumTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if ((IRREGULAR + LABOR).equals(path)) {
			irregularWorkingTime = (IrregularWorkingTimeOfMonthlyDto) value;
		}
	}
}
