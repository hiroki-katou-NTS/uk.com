package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 計算付き月間時間 */
public class TimeMonthWithCalculationDto implements ItemConst {

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
}
