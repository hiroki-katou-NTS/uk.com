package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/** 日別実績の所定内時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithinStatutoryTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 就業時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORK_TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer workTime;

	/** 実働就業時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ACTUAL + WORK_TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer workTimeIncludeVacationTime;

	/** 所定内割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PREMIUM)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer withinPrescribedPremiumTime;

	/** 所定内深夜時間: 所定内深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = LATE_NIGHT)
	private CalcAttachTimeDto withinStatutoryMidNightTime;

	/** 休暇加算時間: 勤怠時間 */
	// TODO: Check id
	// 日別実績の勤怠時間．実績時間．総労働時間．所定内時間．休暇加算時間 年休加算時間 576
	// 日別実績の勤怠時間．実績時間．総労働時間．所定内時間．休暇加算時間 特別休暇加算時間 577
	// 日別実績の勤怠時間．実績時間．総労働時間．所定内時間．休暇加算時間 積立年休加算時間 578
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = HOLIDAY + ADD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer vacationAddTime;

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (LATE_NIGHT.equals(path)) {
			return new CalcAttachTimeDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (LATE_NIGHT.equals(path)) {
			return Optional.ofNullable(withinStatutoryMidNightTime);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (LATE_NIGHT.equals(path)) {
			withinStatutoryMidNightTime = (CalcAttachTimeDto) value;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORK_TIME:
			return Optional.of(ItemValue.builder().value(workTime).valueType(ValueType.TIME));
		case (ACTUAL + WORK_TIME):
			return Optional.of(ItemValue.builder().value(workTimeIncludeVacationTime).valueType(ValueType.TIME));
		case PREMIUM:
			return Optional.of(ItemValue.builder().value(withinPrescribedPremiumTime).valueType(ValueType.TIME));
		case (HOLIDAY + ADD):
			return Optional.of(ItemValue.builder().value(vacationAddTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORK_TIME:
			this.workTime = value.valueOrDefault(null);
			break;
		case (ACTUAL + WORK_TIME):
			this.workTimeIncludeVacationTime = value.valueOrDefault(null);
			break;
		case PREMIUM:
			this.withinPrescribedPremiumTime = value.valueOrDefault(null);
			break;
		case (HOLIDAY + ADD):
			this.vacationAddTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORK_TIME:
		case (ACTUAL + WORK_TIME):
		case PREMIUM:
		case (HOLIDAY + ADD):
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
	
	public static WithinStatutoryTimeDailyPerformDto fromWithinStatutoryTimeDailyPerform(
			WithinStatutoryTimeOfDaily domain) {
		return domain == null ? null: new WithinStatutoryTimeDailyPerformDto(
						getAttendanceTime(domain.getWorkTime()),
						getAttendanceTime(domain.getActualWorkTime()),
						getAttendanceTime(domain.getWithinPrescribedPremiumTime()),
						getWithStatutory(domain.getWithinStatutoryMidNightTime()),
						getAttendanceTime(domain.getVacationAddTime()));
	}
	
	@Override
	public WithinStatutoryTimeDailyPerformDto clone() {
		return new WithinStatutoryTimeDailyPerformDto(workTime,
									workTimeIncludeVacationTime,
									withinPrescribedPremiumTime,
									withinStatutoryMidNightTime == null ? null : withinStatutoryMidNightTime.clone(),
									vacationAddTime);
	}

	private static CalcAttachTimeDto getWithStatutory(WithinStatutoryMidNightTime domain) {
		return domain == null || domain.getTime() == null ? null : CalcAttachTimeDto.toTimeWithCal(domain.getTime());
	}

	public WithinStatutoryTimeOfDaily toDomain() {
		return WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
				toAttendanceTime(workTime),
				toAttendanceTime(workTimeIncludeVacationTime), 
				toAttendanceTime(withinPrescribedPremiumTime),
				new WithinStatutoryMidNightTime(withinStatutoryMidNightTime == null ? TimeDivergenceWithCalculation.defaultValue()
							: withinStatutoryMidNightTime.createTimeDivWithCalc()),
				toAttendanceTime(vacationAddTime));
	}
	
	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? 0 : domain.valueAsMinutes();
	}
	
	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}
}
