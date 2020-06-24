package nts.uk.ctx.at.record.dom.attendanceitem;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

public interface StoredProcedureFactory {

	public void runStoredProcedure(String comId, Optional<AttendanceTimeOfDailyPerformance> attendanceTime, WorkInfoOfDailyPerformance workInfo);
}
