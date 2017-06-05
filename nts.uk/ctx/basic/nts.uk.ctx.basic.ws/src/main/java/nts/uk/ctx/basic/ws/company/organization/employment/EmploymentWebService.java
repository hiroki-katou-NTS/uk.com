/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employment;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.employment.EmploymentFinder;
import nts.uk.ctx.basic.app.find.company.organization.employment.dto.EmploymentFindDto;

/**
 * The Class EmploymentWebService.
 */
@Path("basic/company/organization/employment")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentWebService extends WebService {
	
	/** The finder. */
	@Inject
	private EmploymentFinder finder;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<EmploymentFindDto> findAll() {
		return this.finder.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param employmentCode the employment code
	 * @return the employment find dto
	 */
	@POST
	@Path("findById/{employmentCode}")
	public EmploymentFindDto findById(@PathParam("employmentCode") String employmentCode) {
		return this.finder.getById(employmentCode);
	}
}
