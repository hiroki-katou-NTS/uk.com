/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.ws.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.query.app.employee.EmployeeSearchQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;
import nts.uk.query.model.employee.RegulationInfoEmployee;

/**
 * The Class RegulationInfoEmployeeWs.
 */
@Path("query/employee")
@Produces(MediaType.APPLICATION_JSON)
public class RegulationInfoEmployeeWs {

	/** The finder. */
	@Inject
	private RegulationInfoEmployeeFinder finder;

	/**
	 * Find regulation info employee.
	 *
	 * @param query the query
	 * @return the list
	 */
	@POST
	@Path("find")
	public List<RegulationInfoEmployee> findRegulationInfoEmployee(EmployeeSearchQueryDto query) {
		return this.finder.find(query);
	}
}
