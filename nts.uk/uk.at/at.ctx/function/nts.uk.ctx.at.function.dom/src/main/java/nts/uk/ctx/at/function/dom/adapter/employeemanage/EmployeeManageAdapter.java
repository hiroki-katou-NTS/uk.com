package nts.uk.ctx.at.function.dom.adapter.employeemanage;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EmployeeManageAdapter {
	List<String> getListEmpID(String companyID , GeneralDate referenceDate);

}
