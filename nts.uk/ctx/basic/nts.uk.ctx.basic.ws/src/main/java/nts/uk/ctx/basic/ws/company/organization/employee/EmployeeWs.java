/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.employee.EmployeeFinder;
import nts.uk.ctx.basic.app.find.company.organization.employee.dto.EmployeeFindDto;

/**
 * The Class EmployeeWs.
 */
@Path("basic/company/organization/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeWs extends WebService{

	/** The finder. */
	@Inject
	private EmployeeFinder finder;
	
	/**
	 * Inits the.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<EmployeeFindDto> init() {
		return this.finder.findAll();
	}
}
