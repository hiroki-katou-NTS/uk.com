package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.List;

import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@AttendanceItemRoot(rootName = "日別実績の出退勤")
public class TimeLeavingOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "出退勤", isList = true)
	private List<WorkLeaveTimeDto> workAndLeave;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務回数")
	@AttendanceItemValue(itemId = 461, type = ValueType.INTEGER)
	private Integer workNo;
}
