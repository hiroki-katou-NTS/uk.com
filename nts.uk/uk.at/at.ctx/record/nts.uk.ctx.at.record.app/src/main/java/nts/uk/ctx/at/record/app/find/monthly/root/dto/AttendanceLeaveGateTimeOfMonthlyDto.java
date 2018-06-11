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

	/** 出勤前時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "出勤前時間", layout = "A")
	private int timeBeforeAttendance;

	/** 滞在時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "滞在時間", layout = "B")
	private int stayingTime;

	/** 退勤後時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "退勤後時間", layout = "C")
	private int timeAfterLeaveWork;

	/** 不就労時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "不就労時間", layout = "D")
	private int unemployedTime;
	
	public static AttendanceLeaveGateTimeOfMonthlyDto from(AttendanceLeaveGateTimeOfMonthly domain) {
		AttendanceLeaveGateTimeOfMonthlyDto dto = new AttendanceLeaveGateTimeOfMonthlyDto();
		if(domain != null) {
			dto.setStayingTime(domain.getStayingTime() == null ? 0 : domain.getStayingTime().valueAsMinutes());
			dto.setTimeAfterLeaveWork(domain.getTimeAfterLeaveWork() == null ? 0 : domain.getTimeAfterLeaveWork().valueAsMinutes());
			dto.setTimeBeforeAttendance(domain.getTimeBeforeAttendance() == null ? 0 : domain.getTimeBeforeAttendance().valueAsMinutes());
			dto.setUnemployedTime(domain.getUnemployedTime() == null ? 0 : domain.getUnemployedTime().valueAsMinutes());
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
		return new AttendanceTimeMonth(time);
	}
}

