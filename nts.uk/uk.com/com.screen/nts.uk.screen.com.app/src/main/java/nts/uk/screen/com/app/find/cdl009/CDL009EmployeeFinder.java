/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.find.cdl009;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The Class CDL009EmployeeFinder.
 */
@Stateless
public class CDL009EmployeeFinder {

	/** The query repo. */
	@Inject
	CDL009EmployeeQueryRepository queryRepo;

	/**
	 * Search emp by workplace list.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<EmployeeSearchOutput> searchEmpByWorkplaceList(SearchEmpInput input) {
		return this.queryRepo.searchEmpByWorkplaceList(input);
	}
}
