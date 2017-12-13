package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の勤怠時間 */
@Getter
@Setter
public class AttendanceTimeDailyPerformDto implements ConvertibleAttendanceItem {

	/** 年月日: 年月日 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = 1, type = ValueType.DATE)
	private GeneralDate date;

	/** 滞在時間: 日別実績の滞在時間 */
	@AttendanceItemLayout(layout = "B")
	private StayingTimeDto stayingTime;

	/** 予定時間: 日別実績の勤務予定時間 */
	@AttendanceItemLayout(layout = "C")
	private WorkScheduleTimeDailyPerformDto scheduleTime;

	/** 実績時間: 日別実績の勤務実績時間 */
	@AttendanceItemLayout(layout = "D")
	private ActualWorkTimeDailyPerformDto actualWorkTime;

	/** 医療時間: 日別実績の医療時間 */
	@AttendanceItemLayout(layout = "E")
	private MedicalTimeDailyPerformDto medicalTime;

	/** 社員ID: 社員ID */
	@AttendanceItemLayout(layout = "F")
	@AttendanceItemValue(itemId = -1)
	private String employeeID;

	/** 予実差異時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "G")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer budgetTimeVariance;

	/** 不就労時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "H")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer unemployedTime;
}
