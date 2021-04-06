package nts.uk.ctx.at.record.dom.adapter.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceTimeOfDailyAttendanceImport {
	private ActualWorkingTimeOfDailyImport actualWorkingTimeOfDaily;
}
