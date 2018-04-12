package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.attdleavegatetime.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の入退門時間 */
public class AttendanceLeaveGateTimeOfMonthlyDto {

	@AttendanceItemLayout(jpPropertyName = "出勤前時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 出勤前時間: 勤怠月間時間 */
	private Integer timeBeforeAttendance;

	@AttendanceItemLayout(jpPropertyName = "出勤前時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 滞在時間: 勤怠月間時間 */
	private Integer stayingTime;

	@AttendanceItemLayout(jpPropertyName = "出勤前時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 退勤後時間: 勤怠月間時間 */
	private Integer timeAfterLeaveWork;

	@AttendanceItemLayout(jpPropertyName = "出勤前時間", layout = "A")
	@AttendanceItemValue(type = ValueType.INTEGER)
	/** 不就労時間: 勤怠月間時間 */
	private Integer unemployedTime;
	
	public static AttendanceLeaveGateTimeOfMonthlyDto from(AttendanceLeaveGateTimeOfMonthly domain) {
		AttendanceLeaveGateTimeOfMonthlyDto dto = new AttendanceLeaveGateTimeOfMonthlyDto();
		if(domain != null) {
			dto.setStayingTime(domain.getStayingTime() == null ? null : domain.getStayingTime().valueAsMinutes());
			dto.setTimeAfterLeaveWork(domain.getTimeAfterLeaveWork() == null ? null : domain.getTimeAfterLeaveWork().valueAsMinutes());
			dto.setTimeBeforeAttendance(domain.getTimeBeforeAttendance() == null ? null : domain.getTimeBeforeAttendance().valueAsMinutes());
			dto.setUnemployedTime(domain.getUnemployedTime() == null ? null : domain.getUnemployedTime().valueAsMinutes());
		}
		return dto;
	}
	public AttendanceLeaveGateTimeOfMonthly toDomain() {
		return AttendanceLeaveGateTimeOfMonthly.of(
										toAttendanceTimeMonth(timeBeforeAttendance), 
										toAttendanceTimeMonth(timeAfterLeaveWork), 
										toAttendanceTimeMonth(stayingTime), 
										toAttendanceTimeMonth(unemployedTime));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return time == null ? null : new AttendanceTimeMonth(time);
	}
}

