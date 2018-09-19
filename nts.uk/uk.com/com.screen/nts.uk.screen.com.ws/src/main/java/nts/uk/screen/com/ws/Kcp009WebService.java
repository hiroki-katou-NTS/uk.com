/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.infra.query.employee.Kcp009EmployeeQueryProcessor;
import nts.uk.screen.com.infra.query.employee.Kcp009EmployeeSearchData;
import nts.uk.screen.com.infra.query.employee.System;
import nts.uk.screen.com.ws.dto.Kcp009Dto;

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
	@Path("employeesearch#/")
	public Kcp009EmployeeSearchData searchEmployee(Kcp009Dto dto) {
		return this.queryProcessor.searchByCode(dto.getEmployeeCode(),
				System.valueOfCode(dto.getSystem())).get();
	}
}
