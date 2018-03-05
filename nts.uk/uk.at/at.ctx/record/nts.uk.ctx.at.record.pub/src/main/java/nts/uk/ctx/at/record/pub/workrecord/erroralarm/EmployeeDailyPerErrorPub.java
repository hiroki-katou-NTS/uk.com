package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.Optional;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorPub {
	Optional<EmployeeDailyPerErrorPubExport> getByErrorCode(String employeeId, DatePeriod datePeriod, String errorCode);
}
