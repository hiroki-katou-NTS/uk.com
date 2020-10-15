package nts.uk.ctx.at.record.dom.adapter.employeemanage;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EmployeeManageRCAdapter {
	List<String> getListEmpID(String companyID, GeneralDate referenceDate);
}
