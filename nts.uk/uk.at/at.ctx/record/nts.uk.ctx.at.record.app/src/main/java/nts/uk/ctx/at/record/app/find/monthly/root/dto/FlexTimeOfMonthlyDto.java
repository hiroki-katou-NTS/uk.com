package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のフレックス時間 */
public class FlexTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** フレックス繰越時間: フレックス繰越時間 */
	private FlexCarryforwardTimeDto carryforwardTime;

	/** フレックス時間: フレックス時間 */
	private FlexTimeMDto flexTime;

	/** フレックス超過時間: 勤怠月間時間 */
	private int excessTime;

	/** フレックス不足控除時間: フレックス不足控除時間 */
	private FlexShortDeductTimeDto shortDeductTime;

	/** フレックス不足時間: 勤怠月間時間 */
	private int shortageTime;

	/** 時間外超過のフレックス時間: 時間外超過のフレックス時間 */
	private FlexTimeOfExcessOutsideTimeDto excessOutsideTime;
	
	/** 当月精算フレックス時間 */
	private int flexSettleTime;

	public FlexTimeOfMonthly toDomain() {
		return FlexTimeOfMonthly.of(flexTime == null ? new FlexTime() : flexTime.toDomain(),
				new AttendanceTimeMonth(excessTime),
				new AttendanceTimeMonth(shortageTime),
				carryforwardTime == null ? new FlexCarryforwardTime() : carryforwardTime.toDomain(),
				excessOutsideTime == null ? new FlexTimeOfExcessOutsideTime() : excessOutsideTime.toDmain(),
				shortDeductTime == null ? new FlexShortDeductTime() : shortDeductTime.toDomain(),
				new AttendanceTimeMonthWithMinus(flexSettleTime));
	}

	public static FlexTimeOfMonthlyDto from(FlexTimeOfMonthly domain) {
		FlexTimeOfMonthlyDto dto = new FlexTimeOfMonthlyDto();
		if(domain != null) {
			dto.setCarryforwardTime(FlexCarryforwardTimeDto.from(domain.getFlexCarryforwardTime()));
			dto.setFlexTime(FlexTimeMDto.from(domain.getFlexTime()));
			dto.setShortDeductTime(FlexShortDeductTimeDto.from(domain.getFlexShortDeductTime()));
			dto.setExcessOutsideTime(FlexTimeOfExcessOutsideTimeDto.from(domain.getFlexTimeOfExcessOutsideTime()));
			dto.setExcessTime(domain.getFlexExcessTime() == null ? 0 : domain.getFlexExcessTime().valueAsMinutes());
			dto.setShortageTime(domain.getFlexShortageTime() == null ? 0 : domain.getFlexShortageTime().valueAsMinutes());
			dto.setFlexSettleTime(domain.getFlexSettleTime() == null ? 0 : domain.getFlexSettleTime().valueAsMinutes());
		}
		return dto;
	}

	public static FlexTimeOfMonthlyDto from(FlexTimeByPeriod domain) {
		FlexTimeOfMonthlyDto dto = new FlexTimeOfMonthlyDto();
		if(domain != null) {
			dto.setFlexTime(new FlexTimeMDto(
					new FlexTotalTimeDto(
							new TimeMonthWithCalculationDto(domain.getFlexTime().v(), domain.getFlexTime().v()),
							0, 0), 
					domain.getBeforeFlexTime().valueAsMinutes(), null));
			dto.setExcessTime(domain.getFlexExcessTime() == null ? 0 : domain.getFlexExcessTime().valueAsMinutes());
			dto.setShortageTime(domain.getFlexShortageTime() == null ? 0 : domain.getFlexShortageTime().valueAsMinutes());
		}
		return dto;
	}

	public FlexTimeByPeriod toDomainPeriod() {
		return FlexTimeByPeriod.of(
				this.flexTime == null || this.flexTime.getFlexTime() == null
					? new AttendanceTimeMonthWithMinus(0) : this.flexTime.getFlexTime().getFlexTime().toDomainWithMinus().getTime(), 
				new AttendanceTimeMonth(this.excessTime), 
				new AttendanceTimeMonth(this.shortageTime), 
				new AttendanceTimeMonth(this.flexTime == null ? 0 : this.flexTime.getBeforeFlexTime()));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (EXCESS + TIME):
			return Optional.of(ItemValue.builder().value(excessTime).valueType(ValueType.TIME));
		case SHORTAGE:
			return Optional.of(ItemValue.builder().value(shortageTime).valueType(ValueType.TIME));
		case CUR_MONTH + FLEX:
			return Optional.of(ItemValue.builder().value(flexSettleTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case CARRY_FORWARD:
			return new FlexCarryforwardTimeDto();
		case FLEX:
			return new FlexTimeMDto();
		case (SHORTAGE + DEDUCTION):
			return new FlexShortDeductTimeDto();
		case EXCESS:
			return new FlexTimeOfExcessOutsideTimeDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case CARRY_FORWARD:
			return Optional.ofNullable(carryforwardTime);
		case FLEX:
			return Optional.ofNullable(flexTime);
		case (SHORTAGE + DEDUCTION):
			return Optional.ofNullable(shortDeductTime);
		case EXCESS:
			return Optional.ofNullable(excessOutsideTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (EXCESS + TIME):
		case SHORTAGE:
		case CUR_MONTH + FLEX:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (EXCESS + TIME):
			excessTime = value.valueOrDefault(0);
			break;
		case SHORTAGE:
			shortageTime = value.valueOrDefault(0);
			break;
		case CUR_MONTH + FLEX:
			flexSettleTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case CARRY_FORWARD:
			carryforwardTime = (FlexCarryforwardTimeDto) value;
			break;
		case FLEX:
			flexTime = (FlexTimeMDto) value;
			break;
		case (SHORTAGE + DEDUCTION):
			shortDeductTime = (FlexShortDeductTimeDto) value;
			break;
		case EXCESS:
			excessOutsideTime = (FlexTimeOfExcessOutsideTimeDto) value;
			break;
		default:
		}
	}

	
	
}
