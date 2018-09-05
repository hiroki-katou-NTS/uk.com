package nts.uk.ctx.at.function.dom.adapter.employeebasic;

import java.util.List;

public interface SyEmployeeFnAdapter {
	List<EmployeeBasicInfoFnImport> findBySIds(List<String> sIds);
}
