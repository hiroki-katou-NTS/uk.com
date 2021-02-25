package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 計算付き月間時間 */
public class TimeMonthWithCalculationDto implements ItemConst, AttendanceItemDataGate {


	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.TIME)
	/** 時間 */
	private Integer time;

	@AttendanceItemLayout(jpPropertyName = CALC, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	/** 計算時間 */
	private Integer calcTime;

	public TimeMonthWithCalculation toDomain() {
		return new TimeMonthWithCalculation(toTime(time), toTime(calcTime));
	}

	public TimeMonthWithCalculationAndMinus toDomainWithMinus() {
		return new TimeMonthWithCalculationAndMinus(toTimeWithMinus(time), toTimeWithMinus(calcTime));
	}

	public static TimeMonthWithCalculationDto from(TimeMonthWithCalculation domain) {
		return new TimeMonthWithCalculationDto(
								domain.getTime() == null ? 0 : domain.getTime().valueAsMinutes(),
								domain.getCalcTime() == null ? 0 : domain.getCalcTime().valueAsMinutes());
	}

	public static TimeMonthWithCalculationDto from(TimeMonthWithCalculationAndMinus domain) {
		return new TimeMonthWithCalculationDto(
								domain.getTime() == null ? 0 : domain.getTime().valueAsMinutes(),
								domain.getCalcTime() == null ? 0 : domain.getCalcTime().valueAsMinutes());
	}

	private AttendanceTimeMonth toTime(Integer time) {
		if(time != null){
		return new AttendanceTimeMonth(time);
		}
		return new AttendanceTimeMonth(0);
	}

	private AttendanceTimeMonthWithMinus toTimeWithMinus(Integer time) {
		return time == null ? new AttendanceTimeMonthWithMinus(0) : new AttendanceTimeMonthWithMinus(time);
	}
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TIME:
			return Optional.of(ItemValue.builder().value(time).valueType(ValueType.TIME));
		case CALC:
			return Optional.of(ItemValue.builder().value(calcTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TIME:
		case CALC:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TIME:
			time = value.valueOrDefault(null);
			break;
		case CALC:
			calcTime = value.valueOrDefault(null);
			break;
		default:
		}
	}
}
