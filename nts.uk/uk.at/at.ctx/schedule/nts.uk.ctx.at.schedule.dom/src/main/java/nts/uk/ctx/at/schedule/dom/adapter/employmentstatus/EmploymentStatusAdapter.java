package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author sonnh1
 *
 */
public interface EmploymentStatusAdapter {
	
	List<EmploymentStatusImported> findListOfEmployee(List<String> employeeIds, DatePeriod period);
}
