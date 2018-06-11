package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の変形労働時間 */
public class IrregularWorkingTimeOfMonthlyDto implements ItemConst {

	/** 複数月変形途中時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = MULTI_MONTH + MIDDLE, layout = LAYOUT_A)
	private Integer multiMonthIrregularMiddleTime;

	/** 変形期間繰越時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = CARRY_FORWARD, layout = LAYOUT_B)
	private Integer irregularPeriodCarryforwardTime;

	/** 変形労働不足時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_C)
	private Integer irregularWorkingShortageTime;

	/** 変形法定内残業時間 */
	@AttendanceItemLayout(jpPropertyName = LEGAL + OVERTIME, layout = LAYOUT_D)
	private TimeMonthWithCalculationDto irregularLegalOverTime;

	public IrregularWorkingTimeOfMonthly toDomain() {
		return IrregularWorkingTimeOfMonthly.of(
						multiMonthIrregularMiddleTime == null ? null : new AttendanceTimeMonthWithMinus(multiMonthIrregularMiddleTime),
						irregularPeriodCarryforwardTime == null ? null : new AttendanceTimeMonthWithMinus(irregularPeriodCarryforwardTime),
						irregularWorkingShortageTime == null ? null : new AttendanceTimeMonth(irregularWorkingShortageTime), 
						irregularLegalOverTime == null ? null : irregularLegalOverTime.toDomain());
	}
	
	public static IrregularWorkingTimeOfMonthlyDto from(IrregularWorkingTimeOfMonthly domain) {
		IrregularWorkingTimeOfMonthlyDto dto = new IrregularWorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setIrregularLegalOverTime(TimeMonthWithCalculationDto.from(domain.getIrregularLegalOverTime()));
			dto.setIrregularPeriodCarryforwardTime(domain.getIrregularPeriodCarryforwardTime() == null 
					? null : domain.getIrregularPeriodCarryforwardTime().valueAsMinutes());
			dto.setIrregularWorkingShortageTime(domain.getIrregularWorkingShortageTime() == null 
					? null : domain.getIrregularWorkingShortageTime().valueAsMinutes());
			dto.setMultiMonthIrregularMiddleTime(domain.getMultiMonthIrregularMiddleTime() == null 
					? null : domain.getMultiMonthIrregularMiddleTime().valueAsMinutes());
		}
		return dto;
	}
}
