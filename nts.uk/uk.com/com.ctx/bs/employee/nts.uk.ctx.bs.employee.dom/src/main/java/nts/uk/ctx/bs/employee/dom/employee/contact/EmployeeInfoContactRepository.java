package nts.uk.ctx.bs.employee.dom.employee.contact;

import java.util.Optional;

public interface EmployeeInfoContactRepository {
	
	void add(EmployeeInfoContact domain);
	
	void update(EmployeeInfoContact domain);
	
	void delete(String sid);
	
	Optional<EmployeeInfoContact> findByEmpId(String sId);
}
