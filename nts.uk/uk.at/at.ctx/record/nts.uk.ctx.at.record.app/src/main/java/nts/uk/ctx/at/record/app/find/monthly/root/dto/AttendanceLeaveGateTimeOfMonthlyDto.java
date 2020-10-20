package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.attendanceleave.AttendanceLeaveGateTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の入退門時間 */
public class AttendanceLeaveGateTimeOfMonthlyDto implements ItemConst {

	/** 出勤前時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = ATTENDANCE, layout = LAYOUT_A)
	private int timeBeforeAttendance;

	/** 滞在時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = STAYING, layout = LAYOUT_B)
	private int stayingTime;

	/** 退勤後時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LEAVE, layout = LAYOUT_C)
	private int timeAfterLeaveWork;

	/** 不就労時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = UNEMPLOYED, layout = LAYOUT_D)
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

