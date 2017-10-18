package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import java.util.List;

public interface EmpFileManagementRepository {
	
	void insert(EmployeeFileManagement domain);
	
	void remove(EmployeeFileManagement domain);
	
	List<EmployeeFileManagement> getDataByParams(String employeeId, int fileType);

}
