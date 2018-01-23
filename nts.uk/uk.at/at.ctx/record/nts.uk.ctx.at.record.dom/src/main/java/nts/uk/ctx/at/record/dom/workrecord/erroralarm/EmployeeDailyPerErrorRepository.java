package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorRepository {
	
	void insert(EmployeeDailyPerError employeeDailyPerformanceError);
	
	void update(EmployeeDailyPerError employeeDailyPerformanceError);
	
	boolean checkExistErrorCode(String employeeID, GeneralDate processingDate, String errorCode);
	
	EmployeeDailyPerError find(String employeeID, GeneralDate processingDate);
	
	List<EmployeeDailyPerError> finds(List<String> employeeID, DatePeriod processingDate);
	
}
