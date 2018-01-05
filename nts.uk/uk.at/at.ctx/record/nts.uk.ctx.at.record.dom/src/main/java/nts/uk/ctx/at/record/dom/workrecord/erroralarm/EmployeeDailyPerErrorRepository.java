package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import nts.arc.time.GeneralDate;

public interface EmployeeDailyPerErrorRepository {
	
	void insert(EmployeeDailyPerError employeeDailyPerformanceError);
	
	void update(EmployeeDailyPerError employeeDailyPerformanceError);
	
	boolean checkExistErrorCode(String employeeID, GeneralDate processingDate, String errorCode);
	
	EmployeeDailyPerError find(String employeeID, GeneralDate processingDate);
	
}
