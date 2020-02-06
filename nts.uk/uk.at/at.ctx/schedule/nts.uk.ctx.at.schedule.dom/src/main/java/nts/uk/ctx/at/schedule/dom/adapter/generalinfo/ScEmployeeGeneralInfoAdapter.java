package nts.uk.ctx.at.schedule.dom.adapter.generalinfo;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
public interface ScEmployeeGeneralInfoAdapter {

	EmployeeGeneralInfoImported getPerEmpInfo(List<String> employeeIds, DatePeriod period);
}
