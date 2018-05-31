package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.List;
import java.util.Map;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyAttendanceItemDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceEmployeeDto;

public interface MonthlyPerformanceScreenRepo {
	/** Get list workplace of login user */
	Map<String, String> getListWorkplace(String employeeId, DateRange dateRange);
	/** Get list employee by jobTitle, employment, workplace, classification */
	List<MonthlyPerformanceEmployeeDto> getListEmployee(List<String> lstJobTitle, List<String> lstEmployment,
			Map<String, String> lstWorkplace, List<String> lstClassification);
	/** Get list business type of list employee (no duplicated) */
	List<String> getListBusinessType(List<String> lstEmployee, DateRange dateRange);
	
	List<MonthlyAttendanceItemDto> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds);
}
