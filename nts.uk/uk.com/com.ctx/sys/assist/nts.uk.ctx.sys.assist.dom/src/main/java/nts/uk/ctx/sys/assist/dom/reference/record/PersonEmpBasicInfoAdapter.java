package nts.uk.ctx.sys.assist.dom.reference.record;

import java.util.List;

public interface PersonEmpBasicInfoAdapter {
	
	/**
	 * Get employee code by employee ID
	 * @return String
	 */
	List<PersonEmpBasicInfoImport> getEmployeeCodeByEmpId(String empId);

}
