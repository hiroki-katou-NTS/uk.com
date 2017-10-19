package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import java.util.List;
import java.util.Optional;

public interface EmpFileManagementRepository {
	
	void insert(EmployeeFileManagement domain);
	
	void update(EmployeeFileManagement domain);
	
	void remove(EmployeeFileManagement domain);
	
	List<EmployeeFileManagement> getDataByParams(String employeeId, int fileType);
	
	Optional<EmployeeFileManagement> getEmpMana(String fileid);

	List<Object> getListDocumentFile(String employeeId, int fileType);
	
}
