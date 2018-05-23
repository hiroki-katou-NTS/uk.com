package nts.uk.ctx.bs.employee.dom.employee.contact;

import java.util.List;
import java.util.Optional;

public interface EmployeeInfoContactRepository {
	
	void add(EmployeeInfoContact domain);
	
	void update(EmployeeInfoContact domain);
	
	void delete(String sid);
	
	Optional<EmployeeInfoContact> findByEmpId(String sId);
	
	List<EmployeeInfoContact> findByListEmpId(List<String> employeeIds);
}
