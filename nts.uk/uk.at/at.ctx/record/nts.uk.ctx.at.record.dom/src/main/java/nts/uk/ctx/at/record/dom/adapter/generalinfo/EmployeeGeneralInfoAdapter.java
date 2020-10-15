package nts.uk.ctx.at.record.dom.adapter.generalinfo;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;

public interface EmployeeGeneralInfoAdapter {

	EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period,boolean checkEmployment,
			boolean checkClassification, boolean checkJobTitle, boolean checkWorkplace, boolean checkDepartment);
	
}
