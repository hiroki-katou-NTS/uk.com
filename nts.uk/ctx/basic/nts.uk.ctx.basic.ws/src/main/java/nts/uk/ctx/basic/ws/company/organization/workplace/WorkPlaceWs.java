/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.basic.app.find.company.organization.workplace.WorkplaceFinder;
import nts.uk.ctx.basic.app.find.company.organization.workplace.dto.WorkplaceFindDto;

/**
 * The Class WorkPlaceWs.
 */
@Path("basic/company/organization/workplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkPlaceWs {

	/** The finder. */
	@Inject
	private WorkplaceFinder finder;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findall")
	@POST
	public List<WorkplaceFindDto> findAll() {
		return this.finder.findAll(date, format);
	}
}
