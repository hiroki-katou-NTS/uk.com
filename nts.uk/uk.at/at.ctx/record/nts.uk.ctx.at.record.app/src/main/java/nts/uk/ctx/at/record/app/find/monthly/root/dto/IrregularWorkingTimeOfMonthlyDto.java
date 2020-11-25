package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の変形労働時間 */
public class IrregularWorkingTimeOfMonthlyDto implements ItemConst {

	/** 複数月変形途中時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = MULTI_MONTH + MIDDLE, layout = LAYOUT_A)
	private int multiMonthIrregularMiddleTime;

	/** 変形期間繰越時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = CARRY_FORWARD, layout = LAYOUT_B)
	private int irregularPeriodCarryforwardTime;

	/** 変形労働不足時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_C)
	private int irregularWorkingShortageTime;

	/** 変形法定内残業時間 */
	@AttendanceItemLayout(jpPropertyName = LEGAL + OVERTIME, layout = LAYOUT_D)
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
