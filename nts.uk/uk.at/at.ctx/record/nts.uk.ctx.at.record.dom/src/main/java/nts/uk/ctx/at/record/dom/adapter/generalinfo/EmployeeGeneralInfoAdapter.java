package nts.uk.ctx.at.record.dom.adapter.generalinfo;

import java.util.List;

import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeGeneralInfoAdapter {

	EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period);
	
}
