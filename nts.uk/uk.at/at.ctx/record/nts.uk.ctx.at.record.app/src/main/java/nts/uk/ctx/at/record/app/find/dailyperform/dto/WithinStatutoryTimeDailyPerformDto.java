package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の所定内時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithinStatutoryTimeDailyPerformDto implements ItemConst {

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
