package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@AttendanceItemRoot(rootName = "日別実績の出退勤")
@Data
public class TimeLeavingOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "出退勤", isList = true, listMaxLength = 2, setFieldWithIndex = "workNo")
	private List<WorkLeaveTimeDto> workAndLeave;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務回数")
	@AttendanceItemValue(itemId = 461, type = ValueType.INTEGER)
	private Integer workTimes;
}
