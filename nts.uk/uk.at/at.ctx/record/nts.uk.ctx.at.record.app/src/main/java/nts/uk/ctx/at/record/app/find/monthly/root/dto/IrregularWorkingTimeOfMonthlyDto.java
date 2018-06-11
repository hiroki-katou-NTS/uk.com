package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の変形労働時間 */
public class IrregularWorkingTimeOfMonthlyDto {

	/** 複数月変形途中時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "複数月変形途中時間", layout = "A")
	private int multiMonthIrregularMiddleTime;

	/** 変形期間繰越時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "変形期間繰越時間", layout = "B")
	private int irregularPeriodCarryforwardTime;

	/** 変形労働不足時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "変形労働不足時間", layout = "C")
	private int irregularWorkingShortageTime;

	/** 変形法定内残業時間 */
	@AttendanceItemLayout(jpPropertyName = "変形法定内残業時間", layout = "D")
	private TimeMonthWithCalculationDto irregularLegalOverTime;

	public IrregularWorkingTimeOfMonthly toDomain() {
		return IrregularWorkingTimeOfMonthly.of(
						new AttendanceTimeMonthWithMinus(multiMonthIrregularMiddleTime),
						new AttendanceTimeMonthWithMinus(irregularPeriodCarryforwardTime),
						new AttendanceTimeMonth(irregularWorkingShortageTime), 
						irregularLegalOverTime == null ? new TimeMonthWithCalculation() : irregularLegalOverTime.toDomain());
	}
	
	public static IrregularWorkingTimeOfMonthlyDto from(IrregularWorkingTimeOfMonthly domain) {
		IrregularWorkingTimeOfMonthlyDto dto = new IrregularWorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIrregularLegalOverTime(TimeMonthWithCalculationDto.from(domain.getIrregularLegalOverTime()));
			dto.setIrregularPeriodCarryforwardTime(domain.getIrregularPeriodCarryforwardTime() == null 
					? 0 : domain.getIrregularPeriodCarryforwardTime().valueAsMinutes());
			dto.setIrregularWorkingShortageTime(domain.getIrregularWorkingShortageTime() == null 
					? 0 : domain.getIrregularWorkingShortageTime().valueAsMinutes());
			dto.setMultiMonthIrregularMiddleTime(domain.getMultiMonthIrregularMiddleTime() == null 
					? 0 : domain.getMultiMonthIrregularMiddleTime().valueAsMinutes());
		}
		return dto;
	}
}
