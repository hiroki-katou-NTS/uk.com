package nts.uk.ctx.basic.dom.organization.department;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.primitive.Memo;

public interface DepartmentRepository {
	
	void add(Department department);
	
	void update(Department department);
	
	void remove(String companyCode, DepartmentCode departmentCode, String historyId);
	
	void registerMemo(String companyCode, String historyId, Memo memo);
	
	Optional<Department> findSingleDepartment(String companyCode, DepartmentCode departmentCode, String historyId);
	
	List<Department> findAllByHistory(String companyCode, String historyId);
	
	boolean checkExist(String companyCode);

}
