/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.infra.query.employee.Kcp009EmployeeQueryProcessor;
import nts.uk.screen.com.infra.query.employee.Kcp009EmployeeSearchData;
import nts.uk.screen.com.infra.query.employee.System;

/**
 * The Class Kcp009WebService.
 */
@Path("screen/com/kcp009")
@Produces("application/json")
public class Kcp009WebService extends WebService {
	
	/** The query processor. */
	@Inject
	private Kcp009EmployeeQueryProcessor queryProcessor;
	
	/**
	 * Search employee.
	 *
	 * @param employeeCode the employee code
	 * @param system the system
	 * @return the kcp 009 employee search data
	 */
	@POST
	@Path("employeesearch/{empCode}/{system}")
	public Kcp009EmployeeSearchData searchEmployee(@PathParam("empCode") String employeeCode,
			@PathParam("system") String system) {
		return this.queryProcessor.searchByCode(employeeCode, System.valueOfCode(system)).get();
	}
}
