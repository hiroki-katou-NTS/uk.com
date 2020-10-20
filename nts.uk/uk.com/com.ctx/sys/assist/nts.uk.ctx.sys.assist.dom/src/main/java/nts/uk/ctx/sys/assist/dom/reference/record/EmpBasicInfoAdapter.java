package nts.uk.ctx.sys.assist.dom.reference.record;

import java.util.List;

public interface EmpBasicInfoAdapter {
	
	/**
	 * Get employee code by employee ID
	 * @return String
	 */
	List<EmpBasicInfoImport> getEmployeeCodeByEmpId(String empId);

}
