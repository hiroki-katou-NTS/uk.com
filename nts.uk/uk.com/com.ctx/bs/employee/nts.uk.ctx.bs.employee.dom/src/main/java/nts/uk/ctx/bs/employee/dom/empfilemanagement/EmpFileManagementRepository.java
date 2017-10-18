package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import java.util.List;

public interface EmpFileManagementRepository {
	
	List<EmployeeFileManagement> getAll();
	
	void insert(EmployeeFileManagement domain);
	
	void remove(EmployeeFileManagement domain);

}
