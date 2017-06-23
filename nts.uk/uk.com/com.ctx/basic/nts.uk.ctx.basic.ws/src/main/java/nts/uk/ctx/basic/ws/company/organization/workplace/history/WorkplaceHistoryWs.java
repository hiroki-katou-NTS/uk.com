/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.workplace.history;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.workplace.history.WorkplaceHistoryFinder;
import nts.uk.ctx.basic.app.find.company.organization.workplace.history.dto.WorkplaceHistoryDto;
import nts.uk.ctx.basic.app.find.company.organization.workplace.history.dto.WorkplaceHistoryInDto;

/**
 * The Class WorkplaceHistoryWs.
 */
@Path("basic/company/organization/workplace/history")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceHistoryWs extends WebService{

	/** The finder. */
	@Inject
	private WorkplaceHistoryFinder finder;
	
	
	/**
	 * Search data.
	 *
	 * @param input the input
	 * @return the list
	 */
	@Path("searchData")
	@POST
	public List<WorkplaceHistoryDto> searchData(WorkplaceHistoryInDto input) {
		return this.finder.findByWorkplace(input);
	}
}
