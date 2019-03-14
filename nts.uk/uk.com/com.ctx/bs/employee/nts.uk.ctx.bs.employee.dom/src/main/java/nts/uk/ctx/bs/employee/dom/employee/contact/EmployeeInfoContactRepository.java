package nts.uk.ctx.bs.employee.dom.employee.contact;

import java.util.List;
import java.util.Optional;

public interface EmployeeInfoContactRepository {
	
	void add(EmployeeInfoContact domain);
	
	void addAll(List<EmployeeInfoContact> domains);
	
	void update(EmployeeInfoContact domain);
	
	void updateAll(List<EmployeeInfoContact> domains);
	
	void delete(String sid);
	
	Optional<EmployeeInfoContact> findByEmpId(String sId);
	
	List<EmployeeInfoContact> findByListEmpId(List<String> employeeIds);
}
