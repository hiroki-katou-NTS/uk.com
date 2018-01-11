package nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の入退門")
public class AttendanceLeavingGateOfDailyDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "入退門時刻", isList = true, listMaxLength = 3, setFieldWithIndex = "timeSheetNo")
	private List<TimeSheetDto> attendanceLeavingGateTime;
}
