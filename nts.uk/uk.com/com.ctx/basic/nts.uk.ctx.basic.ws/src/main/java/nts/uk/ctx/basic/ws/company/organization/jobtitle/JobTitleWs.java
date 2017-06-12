/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.jobtitle;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.basic.app.find.company.organization.jobtitle.JobTitleFinder;
import nts.uk.ctx.basic.app.find.company.organization.jobtitle.dto.JobTitleDto;

/**
 * The Class JobTitleWs.
 */
@Path("basic/company/organization/jobtitle")
@Produces(MediaType.APPLICATION_JSON)
public class JobTitleWs {

	/** The finder. */
	@Inject
	private JobTitleFinder finder;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findall")
	@POST
	public List<JobTitleDto> findAll(String referenceDate) {
		return this.finder.findAll(referenceDate);
	}
}
