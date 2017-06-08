/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.closure;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.workrecord.closure.ClosureFinder;

/**
 * The Class ClosureWs.
 */
@Path("ctx/at/record/workrecord/closure")
@Produces("application/json")
public class ClosureWs {
	
	/** The finder. */
	@Inject
	private ClosureFinder finder;
	
}
