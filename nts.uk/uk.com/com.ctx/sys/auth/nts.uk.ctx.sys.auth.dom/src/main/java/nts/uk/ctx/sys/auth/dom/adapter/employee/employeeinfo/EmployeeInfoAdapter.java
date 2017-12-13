package nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EmployeeInfoAdapter {

	List<EmployeeInfoImport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate);
	
}
