package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

@Data
/**　時間帯　*/
public class TimeSheetDto {
	
	private Integer timeSheetNo;

	@AttendanceItemLayout(layout="A", jpPropertyName="開始時間")
	private TimeStampDto start;

	@AttendanceItemLayout(layout="B", jpPropertyName="終了時間")
	private TimeStampDto end;
}
