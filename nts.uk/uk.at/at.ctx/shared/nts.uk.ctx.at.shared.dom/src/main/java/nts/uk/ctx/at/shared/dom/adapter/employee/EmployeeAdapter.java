/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.util.List;

/**
 * The Interface EmployeeAdapter.
 */
public interface EmployeeAdapter {

	/**
	 * Find by list id.
	 *
	 * @param empIdList the emp id list
	 * @return the list
	 */
	List<EmployeeImported> findByListId(List<String> empIdList);
}
