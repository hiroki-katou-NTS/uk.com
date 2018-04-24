package nts.uk.ctx.at.request.dom.application.common.adapter.bs.employee;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.employee.dto.EmployeeInfoImport;

public interface AtEmployeeRequestAdapter {
	List<EmployeeInfoImport> getByListSID(List<String> sIds);
}
