/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.closure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.workrecord.closure.ClosureHistoryFinder;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryFindDto;

/**
 * The Class ClosureHistoryWs.
 */
@Path("ctx/at/record/workrecord/closure/history")
@Produces("application/json")
public class ClosureHistoryWs {

	/** The finder. */
	@Inject
	private ClosureHistoryFinder finder;
	
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@POST
	@Path("getall")
	public List<ClosureHistoryFindDto> getAll(){
		return this.finder.getAllClosureHistory();
	}
}
