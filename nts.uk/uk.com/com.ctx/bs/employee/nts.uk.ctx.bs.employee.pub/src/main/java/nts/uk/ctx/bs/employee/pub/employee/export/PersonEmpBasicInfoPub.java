package nts.uk.ctx.bs.employee.pub.employee.export;

import java.util.List;

import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;

public interface PersonEmpBasicInfoPub {

	List<EmployeeBasicInfoExport> getFromEmployeeIdList(List<String> employeeIds);

}
