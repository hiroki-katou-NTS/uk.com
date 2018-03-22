package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorRepository {
	
	void insert(EmployeeDailyPerError employeeDailyPerformanceError);
	
	void update(EmployeeDailyPerError employeeDailyPerformanceError);
	
	boolean checkExistErrorCode(String employeeID, GeneralDate processingDate, String errorCode);
	
	boolean checkExistErrorCodeByPeriod(String employeeID, DatePeriod datePeriod, String errorCode);
	
	EmployeeDailyPerError find(String employeeID, GeneralDate processingDate);
	
	List<EmployeeDailyPerError> findList(String companyID,String employeeID);
	
	List<EmployeeDailyPerError> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
	
	List<EmployeeDailyPerError> finds(List<String> employeeID, DatePeriod processingDate);
	
	List<EmployeeDailyPerError> findAll(String employeeID, GeneralDate processingDate);
	
	void removeParam(String sid, GeneralDate date);
	
}
