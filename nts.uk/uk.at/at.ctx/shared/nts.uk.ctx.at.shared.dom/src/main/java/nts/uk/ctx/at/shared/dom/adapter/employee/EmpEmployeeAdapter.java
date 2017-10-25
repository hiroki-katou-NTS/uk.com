/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.util.List;

/**
 * The Interface EmpEmployeeAdapter.
 */
public interface EmpEmployeeAdapter {

	/**
	 * Find by list id.
	 *
	 * @param companyId the company id
	 * @param empIdList the emp id list
	 * @return the list
	 */
	List<EmployeeImported> findByListId(String companyId, List<String> empIdList);
}
