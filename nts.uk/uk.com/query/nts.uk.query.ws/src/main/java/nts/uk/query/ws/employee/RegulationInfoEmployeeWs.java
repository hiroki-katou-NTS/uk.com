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

import nts.uk.query.app.employee.RegulationInfoEmpQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;

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
	public List<RegulationInfoEmployeeDto> findRegulationInfoEmployee(RegulationInfoEmpQueryDto query) {
		return this.finder.find(query);
	}
}
