package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceTimeOfDailyAttendanceExport {
	//実働時間/実績時間  - 日別実績の勤務実績時間 - 勤務時間
	private ActualWorkingTimeOfDailyExport actualWorkingTimeOfDaily;
}