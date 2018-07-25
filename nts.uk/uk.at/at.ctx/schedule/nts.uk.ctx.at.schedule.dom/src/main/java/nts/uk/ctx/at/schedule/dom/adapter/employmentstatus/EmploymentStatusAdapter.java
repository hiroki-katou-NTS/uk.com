package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author sonnh1
 *
 */
public interface EmploymentStatusAdapter {
	
	List<EmploymentStatusImported> findListOfEmployee(List<String> employeeIds, DatePeriod period);
}
