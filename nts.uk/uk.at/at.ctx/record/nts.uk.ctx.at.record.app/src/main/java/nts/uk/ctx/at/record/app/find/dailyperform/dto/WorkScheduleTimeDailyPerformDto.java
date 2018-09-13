package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDailyPerformDto implements ItemConst {

	/** 勤務予定時間: 勤務予定時間 */
	// TODO: confirm item ID and type for 勤務予定時間
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLAN)
	private WorkScheduleTimeDto workSchedule;

	/** 実績所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ACTUAL + FIXED_WORK)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer recordPrescribedLaborTime;

	/** 計画所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = SCHEDULE + FIXED_WORK)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer schedulePrescribedLaborTime;

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
		return domain == null ? null : domain.valueAsMinutes();
	}

	public WorkScheduleTimeOfDaily toDomain() {
		return new WorkScheduleTimeOfDaily(workSchedule == null ? null : new WorkScheduleTime(
						newAttendanceTime(workSchedule.getTotal()),
						newAttendanceTime(workSchedule.getExcessOfStatutoryTime()),
						newAttendanceTime(workSchedule.getWithinStatutoryTime())),
						newAttendanceTime(schedulePrescribedLaborTime), 
						newAttendanceTime(recordPrescribedLaborTime));
	}

	private AttendanceTime newAttendanceTime(Integer time) {
		return time == null ? new AttendanceTime(0) : new AttendanceTime(time);
	}
}
