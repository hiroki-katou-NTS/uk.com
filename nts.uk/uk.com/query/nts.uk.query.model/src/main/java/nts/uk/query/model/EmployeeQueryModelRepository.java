/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model;

import java.util.List;

/**
 * The Interface EmployeeQueryModelRepository.
 */
public interface EmployeeQueryModelRepository {
	
	/**
	 * Find.
	 *
	 * @param paramQuery the param query
	 * @return the list
	 */
	public List<EmployeeQueryModel> find(EmployeeSearchQuery paramQuery);

}
