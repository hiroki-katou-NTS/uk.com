package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;

/** 日別実績の勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 勤務予定時間: 勤務予定時間 */
	// TODO: confirm item ID and type for 勤務予定時間
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLAN)
	private WorkScheduleTimeDto workSchedule;

	/** 実績所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ACTUAL + FIXED_WORK)
	@AttendanceItemValue(type = ValueType.TIME)
	private int recordPrescribedLaborTime;

	/** 計画所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = SCHEDULE + FIXED_WORK)
	@AttendanceItemValue(type = ValueType.TIME)
	private int schedulePrescribedLaborTime;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (ACTUAL + FIXED_WORK):
			return Optional.of(ItemValue.builder().value(recordPrescribedLaborTime).valueType(ValueType.TIME));
		case (SCHEDULE + FIXED_WORK):
			return Optional.of(ItemValue.builder().value(schedulePrescribedLaborTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (ACTUAL + FIXED_WORK):
		case (SCHEDULE + FIXED_WORK):
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (PLAN.equals(path)) {
			return new WorkScheduleTimeDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (PLAN.equals(path)) {
			return Optional.ofNullable(workSchedule);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (ACTUAL + FIXED_WORK):
			recordPrescribedLaborTime = value.valueOrDefault(0);
		case (SCHEDULE + FIXED_WORK):
			schedulePrescribedLaborTime = value.valueOrDefault(0);
		default:
			break;
		}
		AttendanceItemDataGate.super.set(path, value);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (path.equals(PLAN)) {
			workSchedule = (WorkScheduleTimeDto) value;
		}
	}
	
	@Override
	public WorkScheduleTimeDailyPerformDto clone() {
		return new WorkScheduleTimeDailyPerformDto(workSchedule == null ? null : workSchedule.clone(),
																				recordPrescribedLaborTime,
																				schedulePrescribedLaborTime);
	}

	public static WorkScheduleTimeDailyPerformDto fromWorkScheduleTime(WorkScheduleTimeOfDaily domain) {
		return domain == null ? null : new WorkScheduleTimeDailyPerformDto(
						getWorkSchedule(domain.getWorkScheduleTime()),
						getAttendanceTime(domain.getRecordPrescribedLaborTime()),
						getAttendanceTime(domain.getSchedulePrescribedLaborTime()));
	}

	private static WorkScheduleTimeDto getWorkSchedule(WorkScheduleTime domain) {
		return domain == null ? null : new WorkScheduleTimeDto(
							getAttendanceTime(domain.getTotal()),
							getAttendanceTime(domain.getExcessOfStatutoryTime()),
							getAttendanceTime(domain.getWithinStatutoryTime()));
	}

	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? 0 : domain.valueAsMinutes();
	}

	public WorkScheduleTimeOfDaily toDomain() {
		return new WorkScheduleTimeOfDaily(workSchedule == null ? WorkScheduleTime.defaultValue() : new WorkScheduleTime(
																		new AttendanceTime(workSchedule.getTotal()),
																		new AttendanceTime(workSchedule.getExcessOfStatutoryTime()),
																		new AttendanceTime(workSchedule.getWithinStatutoryTime())),
																		new AttendanceTime(schedulePrescribedLaborTime), 
																		new AttendanceTime(recordPrescribedLaborTime));
	}
}
