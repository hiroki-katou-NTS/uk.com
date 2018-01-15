package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の勤怠時間 */
@Getter
@Setter
@AttendanceItemRoot(rootName = "日別実績の勤怠時間")
public class AttendanceTimeDailyPerformDto implements ConvertibleAttendanceItem {

	//TODO: there are not map item id
	/** 年月日: 年月日 */
	private GeneralDate date;

	/** 社員ID: 社員ID */
	private String employeeID;

	/** 実績時間: 日別実績の勤務実績時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "実績時間")
	private ActualWorkTimeDailyPerformDto actualWorkTime;

	/** 勤務予定時間: 日別実績の勤務予定時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務予定時間")
	private WorkScheduleTimeDailyPerformDto scheduleTime;

	/** 滞在時間: 日別実績の滞在時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "滞在時間")
	private StayingTimeDto stayingTime;

	/** 医療時間: 日別実績の医療時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "医療時間")
	private MedicalTimeDailyPerformDto medicalTime;

	/** 予実差異時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "予実差異時間")
	@AttendanceItemValue(itemId = 552, type = ValueType.INTEGER)
	private Integer budgetTimeVariance;

	/** 不就労時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "不就労時間")
	@AttendanceItemValue(itemId = 554, type = ValueType.INTEGER)
	private Integer unemployedTime;
}
