package nts.uk.ctx.at.record.dom.adapter.employee;

import java.util.List;

public interface EmployeeRecordAdapter {
	
	EmployeeRecordImport getPersonInfor(String employeeId);
	
	List<EmployeeRecordImport> getPersonInfor(List<String> listEmployeeId);

}
