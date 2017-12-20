package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

@Data
/** 出退勤時刻 */
public class WorkLeaveTimeDto {

	private Integer workNo;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "出勤")
	private WithActualTimeStampDto working;
	
	@AttendanceItemLayout(layout = "B", jpPropertyName = "退勤")
	private WithActualTimeStampDto leave;
}
