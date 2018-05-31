package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の所定労働時間 */
public class PrescribedWorkingTimeOfMonthlyDto {

	/** 計画所定労働時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "計画所定労働時間", layout = "A")
	private Integer scheduleTime;

	/** 実績所定労働時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "実績所定労働時間", layout = "B")
	private Integer recordTime;
	
	public PrescribedWorkingTimeOfMonthly toDomain() {
		return PrescribedWorkingTimeOfMonthly.of(
							scheduleTime == null ? null : new AttendanceTimeMonth(scheduleTime),
							recordTime == null ? null : new AttendanceTimeMonth(recordTime));
	}
	
	public static PrescribedWorkingTimeOfMonthlyDto from(PrescribedWorkingTimeOfMonthly domain) {
		PrescribedWorkingTimeOfMonthlyDto dto = new PrescribedWorkingTimeOfMonthlyDto();
		if(domain != null) {
			dto.setRecordTime(domain.getRecordPrescribedWorkingTime() == null ? null : domain.getRecordPrescribedWorkingTime().valueAsMinutes());
			dto.setScheduleTime(domain.getSchedulePrescribedWorkingTime() == null ? null : domain.getSchedulePrescribedWorkingTime().valueAsMinutes());
		}
		return dto;
	}
}
