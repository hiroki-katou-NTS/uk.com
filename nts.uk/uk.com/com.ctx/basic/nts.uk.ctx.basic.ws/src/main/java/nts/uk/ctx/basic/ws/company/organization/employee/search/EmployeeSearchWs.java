/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employee.search;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.app.find.company.organization.employee.search.EmployeeSearchDto;
import nts.uk.ctx.basic.app.find.company.organization.employee.search.EmployeeSearchFinder;
import nts.uk.ctx.basic.app.find.person.PersonDto;

/**
 * The Class EmployeeSearchWs.
 */
@Path("basic/organization/employee/search")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeSearchWs extends WebService {
	
	/** The finder. */
	@Inject
	private EmployeeSearchFinder finder;
	
	/**
	 * Search mode employee.
	 *
	 * @param input the input
	 * @return the list
	 */
	@POST
	@Path("advanced")
	public List<PersonDto> searchModeEmployee(EmployeeSearchDto input) {
		return this.finder.searchModeEmployee(input);
	}
	
	
	/**
	 * Search of workplace.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("ofworkplace")
	public List<PersonDto> searchOfWorkplace(GeneralDate baseDate){
		return this.finder.searchOfWorkplace(baseDate);
	}
	
	/**
	 * Search of work place child.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("workplacechild")
	public List<PersonDto> searchWorkplaceChild(GeneralDate baseDate){
		return this.finder.searchWorkplaceChild(baseDate);
	}
}
