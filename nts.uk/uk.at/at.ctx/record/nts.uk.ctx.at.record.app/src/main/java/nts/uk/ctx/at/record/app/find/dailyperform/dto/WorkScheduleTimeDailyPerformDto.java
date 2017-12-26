package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDailyPerformDto {

	/** 勤務予定時間: 勤務予定時間 */
	//TODO: confirm item ID and type for 勤務予定時間 
//	private WorkScheduleTimeDto workSchedule;
	@AttendanceItemLayout(layout = "A", jpPropertyName = "勤務予定時間")
	@AttendanceItemValue(itemId = 529, type = ValueType.INTEGER)
	private Integer workSchedule;

	/** 実績所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "実績所定労働時間")
	@AttendanceItemValue(itemId = 531, type = ValueType.INTEGER)
	private Integer recordPrescribedLaborTime;

	/** 計画所定労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "計画所定労働時間")
	@AttendanceItemValue(itemId = 530, type = ValueType.INTEGER)
	private Integer schedulePrescribedLaborTime;
	
	public static WorkScheduleTimeDailyPerformDto fromWorkScheduleTime(WorkScheduleTimeOfDaily domain) {
		return domain == null ? null : new WorkScheduleTimeDailyPerformDto(
				domain.getWorkScheduleTime().getTotal().valueAsMinutes(), 
				domain.getRecordPrescribedLaborTime().valueAsMinutes(), 
				domain.getSchedulePrescribedLaborTime().valueAsMinutes());
	}
}
