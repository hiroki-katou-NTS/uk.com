package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDailyPerformDto {

	/** 勤務予定時間: 勤務予定時間 */
	// TODO: confirm item ID and type for 勤務予定時間
	@AttendanceItemLayout(layout = "C", jpPropertyName = "予定時間")
	private WorkScheduleTimeDto workSchedule;
	// @AttendanceItemLayout(layout = "A", jpPropertyName = "勤務予定時間")
	// @AttendanceItemValue(itemId = 529, type = ValueType.INTEGER)
	// private Integer workSchedule;

	/** 実績所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "実績所定労働時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer recordPrescribedLaborTime;

	/** 計画所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "計画所定労働時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
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
		return time == null ? null : new AttendanceTime(time);
	}
}
