package nts.uk.ctx.at.function.dom.statement;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;

public interface EmployeeGeneralInfoAdapter {

	EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period,boolean checkEmployment,
			boolean checkClassification, boolean checkJobTitle, boolean checkWorkplace, boolean checkDepartment);
	
}