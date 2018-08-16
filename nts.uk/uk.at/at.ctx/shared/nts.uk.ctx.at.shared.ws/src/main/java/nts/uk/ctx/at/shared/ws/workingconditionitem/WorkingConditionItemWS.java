/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workingconditionitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.workingconditionitem.WorkingConditionItemFinder;

/**
 * The Class WorkingConditionItemWS.
 */
@Path("ctx/at/shared/wcitem/")
@Produces("application/json")
public class WorkingConditionItemWS {

	/** The Working condition item finder. */
	@Inject
	private WorkingConditionItemFinder WorkingConditionItemFinder;

	/**
	 * Filter sids.
	 *
	 * @param lstSid
	 *            the lst sid
	 * @return the list
	 */
	@POST
	@Path("filter/sids")
	public List<String> filterSids(List<String> employeeIds) {
		return this.WorkingConditionItemFinder.findBySidsAndNewestHistory(employeeIds);
	}

}
