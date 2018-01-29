package nts.uk.ctx.bs.employee.dom.employeeinfo.contact;

import java.util.Optional;

public interface EmployeeInfoContactRepository {
	
	Optional<EmployeeInfoContact> findByEmpId(String sId);

}
