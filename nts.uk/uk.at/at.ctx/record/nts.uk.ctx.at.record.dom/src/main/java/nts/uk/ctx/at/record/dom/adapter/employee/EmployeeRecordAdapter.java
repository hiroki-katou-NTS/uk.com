package nts.uk.ctx.at.record.dom.adapter.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRecordAdapter {
	
	EmployeeRecordImport getPersonInfor(String employeeId);
	
	List<EmployeeRecordImport> getPersonInfor(List<String> listEmployeeId);
	
	List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sids);
	
	Optional<EmployeeDataMngInfoImport> findByScdNotDel(String employeeCd, String companyId);

}
