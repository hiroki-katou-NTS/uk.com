package nts.uk.query.app.ccg005.query.work.information.work.performance;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.query.app.ccg005.query.work.information.work.performance.dto.WorkInfoOfDailyAttendanceDto;

@Data
@Builder
public class WorkInfoOfDailyPerformanceDto {

	// 社員ID
	private String employeeId;
	// 年月日
	private GeneralDate ymd;
	// 勤務情報
	private WorkInfoOfDailyAttendanceDto workInformation;
}
