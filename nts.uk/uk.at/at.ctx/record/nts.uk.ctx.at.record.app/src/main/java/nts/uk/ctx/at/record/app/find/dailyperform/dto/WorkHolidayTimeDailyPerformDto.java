package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の休出時間 */
@Data
public class WorkHolidayTimeDailyPerformDto {

	/** 休出枠時間帯: 休出枠時間帯 */
//	@AttendanceItemLayout(layout = "A", isList = true)
	private List<HolidayWorkFrameTimeSheetDto> holidyWorkFrameTimeSheet;

	/** 休出深夜: 休出深夜 */
	@AttendanceItemLayout(layout = "B", jpPropertyName="休出深夜")
	private HolidayMidnightWorkDto holidayMidnightWork;

	/** 休出拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName="休出拘束時間")
	@AttendanceItemValue(itemId = 746, type = ValueType.INTEGER)
	private Integer holidayTimeSpentAtWork;

	/** 休出枠時間: 休出枠時間 */
	@AttendanceItemLayout(layout = "D", isList = true, jpPropertyName="休出枠時間")
	private List<HolidayWorkFrameTimeDto> holidayWorkFrameTime;
}
