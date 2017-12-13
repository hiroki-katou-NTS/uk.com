package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の休出時間 */
@Data
public class WorkHolidayTimeDailyPerformDto {

	/** 休出拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int holidayTimeSpentAtWork;
	
	/** 休出枠時間帯: 休出枠時間帯 */
	@AttendanceItemLayout(layout="B", isList=true)
	private List<HolidayWorkFrameTimeSheetDto> holidyWorkFrameTimeSheet;
	
	/** 休出枠時間: 休出枠時間 */
	@AttendanceItemLayout(layout="C", isList=true)
	private List<HolidayWorkFrameTimeDto> holidayWorkFrameTime;
	
	/** 休出深夜: 休出深夜 */
	@AttendanceItemLayout(layout="D")
	private HolidayMidnightWorkDto holidayMidnightWork;
}
