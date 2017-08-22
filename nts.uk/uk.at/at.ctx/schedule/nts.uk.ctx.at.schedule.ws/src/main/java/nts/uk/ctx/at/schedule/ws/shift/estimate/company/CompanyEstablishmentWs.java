/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.company;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.CompanyEstablishmentFinder;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto.CompanyEstimateTimeDto;

/**
 * The Class CompanyEstablishmentWs.
 */
@Path("ctx/at/schedule/shift/estimate/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyEstablishmentWs extends WebService{

	/** The finder. */
	@Inject
	private CompanyEstablishmentFinder finder;

	/**
	 * Find by target year.
	 *
	 * @param targetYear the target year
	 * @return the company estimate time dto
	 */
	@POST
	@Path("find/{targetYear}")
	public CompanyEstimateTimeDto findByTargetYear(@PathParam("targetYear") Integer targetYear) {
		return this.finder.findEstimateTime(targetYear);
	}
}
