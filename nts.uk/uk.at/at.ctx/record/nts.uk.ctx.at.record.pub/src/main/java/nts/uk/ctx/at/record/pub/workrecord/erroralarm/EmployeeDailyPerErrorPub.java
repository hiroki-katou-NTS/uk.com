package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorPub {
	
	List<EmployeeDailyPerErrorPubExport> getByErrorCode(String employeeId, DatePeriod datePeriod, List<String> errorCodes);
	
	List<EmployeeDailyPerErrorPubExport> getByErrorCode(List<String> employeeId, DatePeriod datePeriod, List<String> errorCodes);
}
