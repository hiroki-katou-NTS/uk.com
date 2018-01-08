package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
@AttendanceItemRoot(rootName = "日別実績の臨時出退勤")
public class TemporaryTimeOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	private String employeeId;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "勤務回数")
	@AttendanceItemValue(type = ValueType.INTEGER, itemId = 616)
	private Integer workTimes;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "出退勤", isList = true, listMaxLength = 3, setFieldWithIndex = "workNo")
	private List<WorkLeaveTimeDto> workLeaveTime;

	private GeneralDate ymd;
}
