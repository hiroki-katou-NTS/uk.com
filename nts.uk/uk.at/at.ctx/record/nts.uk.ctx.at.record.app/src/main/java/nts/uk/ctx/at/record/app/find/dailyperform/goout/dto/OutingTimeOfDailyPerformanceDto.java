package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import java.util.List;

import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "日別実績の外出時間帯")
public class OutingTimeOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", isList = true)
	private List<OutingTimeZoneDto> timeZone;
}
