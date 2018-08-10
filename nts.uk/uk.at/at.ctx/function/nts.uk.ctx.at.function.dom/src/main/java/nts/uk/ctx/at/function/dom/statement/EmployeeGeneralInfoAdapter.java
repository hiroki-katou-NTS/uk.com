package nts.uk.ctx.at.function.dom.statement;

import java.util.List;

import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeGeneralInfoAdapter {

	EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period,boolean checkEmployment,
			boolean checkClassification, boolean checkJobTitle, boolean checkWorkplace, boolean checkDepartment);
	
}