/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import java.util.List;

/**
 * The Interface EmployeeInfoRepository.
 */
public interface EmployeeInfoRepository {
	
	/**
	 * Find.
	 *
	 * @param query the query
	 * @return the list
	 */
	List<EmployeeInfoResultModel> find(EmployeeInfoQueryModel query);
}
