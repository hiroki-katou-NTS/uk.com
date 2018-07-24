package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorPub {
	
	List<EmployeeDailyPerErrorPubExport> getByErrorCode(String employeeId, DatePeriod datePeriod, List<String> errorCodes);
	
	/**
	 * RequestList303
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	List<EmployeeErrorPubExport> checkEmployeeErrorOnProcessingDate(String employeeId, DatePeriod datePeriod);

	List<EmployeeDailyPerErrorPubExport> getByErrorCode(List<String> employeeId, DatePeriod datePeriod, List<String> errorCodes);
}
