package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorAdapter {
	List<EmployeeDailyPerErrorImport> getByErrorAlarm(String employeeId, DatePeriod datePeriod, List<String> errorCodes);
	
	List<EmployeeDailyPerErrorImport> getByErrorAlarm(List<String> employeeId, DatePeriod datePeriod, List<String> errorCodes);
}
