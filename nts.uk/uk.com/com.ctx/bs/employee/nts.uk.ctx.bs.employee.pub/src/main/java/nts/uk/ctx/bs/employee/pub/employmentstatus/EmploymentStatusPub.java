package nts.uk.ctx.bs.employee.pub.employmentstatus;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmploymentStatusPub {
	
	List<EmploymentStatus> findListOfEmployee(List<String> employeeIds, DatePeriod period);

}
