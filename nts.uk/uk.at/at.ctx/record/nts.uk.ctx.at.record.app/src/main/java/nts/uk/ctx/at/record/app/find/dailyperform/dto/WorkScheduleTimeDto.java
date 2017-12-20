package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 勤務予定時間 */
@Data
public class WorkScheduleTimeDto {

	/** The person fee time. */
	// 人件費時間
//	@AttendanceItemLayout(layout = "A", isList = true)
	private List<PersonFeeTimeDto> personFeeTime;

	/** The break time. */
	// 休憩時間
//	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakTime;

	/** The working time. */
	// 実働時間
//	@AttendanceItemLayout(layout = "C")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workingTime;

	/** The weekday time. */
	// 平日時間
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer weekdayTime;

	/** The predetermine time. */
	// 所定時間
//	@AttendanceItemLayout(layout = "E")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer predetermineTime;

	/** The total labor time. */
	// 総労働時間
//	@AttendanceItemLayout(layout = "F")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer totalLaborTime;

	/** The child care time. */
	// 育児介護時間
//	@AttendanceItemLayout(layout = "G")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer childCareTime;
}
