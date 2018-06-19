/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws.cdl009;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.com.app.find.cdl009.CDL009EmployeeFinder;
import nts.uk.screen.com.app.find.cdl009.EmployeeSearchOutput;
import nts.uk.screen.com.app.find.cdl009.SearchEmpInput;

/**
 * The Class Cdl009WebService.
 */
@Path("screen/com/cdl009")
@Produces("application/json")
public class Cdl009WebService {
	
	/** The emp finder. */
	@Inject
	private CDL009EmployeeFinder empFinder;
	
	/**
	 * Search employee.
	 *
	 * @param input the input
	 * @return the list
	 */
	@POST
	@Path("searchByWorkplaceList")
	public List<EmployeeSearchOutput> searchEmployee(SearchEmpInput input) {
		return this.empFinder.searchEmpByWorkplaceList(input);
	}
}
