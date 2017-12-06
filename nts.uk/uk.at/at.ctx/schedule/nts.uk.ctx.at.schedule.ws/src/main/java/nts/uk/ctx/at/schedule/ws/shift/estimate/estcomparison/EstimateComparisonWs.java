/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.estcomparison;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.estcomparison.EstimateComparisonSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.estcomparison.EstimateComparisonSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.estcomparison.EstimateComparisonFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.estcomparison.EstimateComparisonFinder;

/**
 * The Class EstimateComparisonWs.
 */
@Path("ctx/at/schedule/shift/estimate/estcomparison")
@Produces("application/json")
public class EstimateComparisonWs extends WebService {

	/** The finder. */
	@Inject 
	private EstimateComparisonFinder finder;
	
	/** The save command handler. */
	@Inject
	private EstimateComparisonSaveCommandHandler saveCommandHandler;
	
	/**
	 * Find by id.
	 *
	 * @return the estimate comparison find dto
	 */
	@POST
	@Path("find")
	public EstimateComparisonFindDto findById () {
		return this.finder.findByCompanyId();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save (EstimateComparisonSaveCommand command) {
		this.saveCommandHandler.handle(command);
	}
	
}
