package nts.uk.ctx.at.request.dom.application.common.adapter.employeemanage;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EmployeeManageRQAdapter {
	List<String> getListEmpID(String companyID, GeneralDate referenceDate);
}
