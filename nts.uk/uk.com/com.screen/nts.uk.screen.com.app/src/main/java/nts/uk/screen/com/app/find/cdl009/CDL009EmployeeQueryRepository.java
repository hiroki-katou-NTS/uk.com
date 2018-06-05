/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.find.cdl009;

import java.util.List;

/**
 * The Interface CDL009EmployeeQueryRepository.
 */
public interface CDL009EmployeeQueryRepository {

	/**
	 * Search emp by workplace list.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<EmployeeSearchOutput> searchEmpByWorkplaceList(SearchEmpInput input);
}
