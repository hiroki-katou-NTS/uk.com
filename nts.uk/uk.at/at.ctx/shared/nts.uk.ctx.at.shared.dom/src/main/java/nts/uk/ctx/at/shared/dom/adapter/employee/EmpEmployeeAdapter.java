/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface EmpEmployeeAdapter.
 */
public interface EmpEmployeeAdapter {

	/**
	 * Find by emp id.
	 *
	 * @param empId the emp id
	 * @return the employee imported
	 */
	// for RequestList #1-2
	EmployeeImport findByEmpId(String empId);
	
	// RequestList335
	List<String> getListEmpByWkpAndEmpt(List<String> wkps , List<String> lstempts , DatePeriod dateperiod);
	
	// RequestList61
	List<PersonEmpBasicInfoImport> getPerEmpBasicInfo(List<String> employeeIds);
}
