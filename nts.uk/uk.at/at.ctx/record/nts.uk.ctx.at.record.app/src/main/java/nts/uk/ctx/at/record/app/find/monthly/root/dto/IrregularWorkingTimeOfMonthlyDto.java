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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の変形労働時間 */
public class IrregularWorkingTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 複数月変形途中時間 */
	private int multiMonthIrregularMiddleTime;

	/** 変形期間繰越時間 */
	private int irregularPeriodCarryforwardTime;

	/** 変形労働不足時間 */
	private int irregularWorkingShortageTime;

	/** 変形法定内残業時間 */
	private TimeMonthWithCalculationDto irregularLegalOverTime;

	/** 変形法定内休暇加算時間 */
	private int legalVacationAddTime;

	/** 変形法定外休暇加算時間 */
	private int illegalVacationAddTime;

	public IrregularWorkingTimeOfMonthly toDomain() {
		return IrregularWorkingTimeOfMonthly.of(
						new AttendanceTimeMonthWithMinus(multiMonthIrregularMiddleTime),
						new AttendanceTimeMonthWithMinus(irregularPeriodCarryforwardTime),
						new AttendanceTimeMonth(irregularWorkingShortageTime), 
						irregularLegalOverTime == null ? new TimeMonthWithCalculation() : irregularLegalOverTime.toDomain(),
						new AttendanceTimeMonth(illegalVacationAddTime),
						new AttendanceTimeMonth(legalVacationAddTime));
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
			dto.setLegalVacationAddTime(domain.getLegalVacationAddTime().valueAsMinutes());
			dto.setIllegalVacationAddTime(domain.getIllegalVacationAddTime().valueAsMinutes());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (MULTI_MONTH + MIDDLE):
			return Optional.of(ItemValue.builder().value(multiMonthIrregularMiddleTime).valueType(ValueType.TIME));
		case CARRY_FORWARD:
			return Optional.of(ItemValue.builder().value(irregularPeriodCarryforwardTime).valueType(ValueType.TIME));
		case SHORTAGE:
			return Optional.of(ItemValue.builder().value(irregularWorkingShortageTime).valueType(ValueType.TIME));
		case LEGAL:
			return Optional.of(ItemValue.builder().value(legalVacationAddTime).valueType(ValueType.TIME));
		case ILLEGAL:
			return Optional.of(ItemValue.builder().value(illegalVacationAddTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if ((LEGAL + OVERTIME).equals(path)) {
			return new TimeMonthWithCalculationDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if ((LEGAL + OVERTIME).equals(path)) {
			return Optional.ofNullable(irregularLegalOverTime);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (MULTI_MONTH + MIDDLE):
		case CARRY_FORWARD:
		case SHORTAGE:
		case LEGAL:
		case ILLEGAL:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (MULTI_MONTH + MIDDLE):
			multiMonthIrregularMiddleTime = value.valueOrDefault(0);
			break;
		case CARRY_FORWARD:
			irregularPeriodCarryforwardTime = value.valueOrDefault(0);
			break;
		case SHORTAGE:
			irregularWorkingShortageTime = value.valueOrDefault(0);
			break;
		case LEGAL:
			legalVacationAddTime = value.valueOrDefault(0);
			break;
		case ILLEGAL:
			illegalVacationAddTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if ((LEGAL + OVERTIME).equals(path)) {
			irregularLegalOverTime = (TimeMonthWithCalculationDto) value;
		}
	}


}
