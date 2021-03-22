/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workingconditionitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.workingconditionitem.UpadateWorkingConditonItemCommandHandler;
import nts.uk.ctx.at.shared.app.command.workingconditionitem.WorkingConditionItemSaveCommand;
import nts.uk.ctx.at.shared.app.find.workingconditionitem.WorkingConditionItemDto;
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
	
	@Inject
	private UpadateWorkingConditonItemCommandHandler upadateWorkingConditonItemCommandHandler;
	

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
	
	
	@POST
	@Path("findOne/{histId}")
	public WorkingConditionItemDto findOne(@PathParam("histId") String histId) {
		return this.WorkingConditionItemFinder.findByHistId(histId) ;
	}
	
	@POST
	@Path("register")
	public void registerWorkConditionItem(WorkingConditionItemSaveCommand command){
		this.upadateWorkingConditonItemCommandHandler.handle(command);		
	}
}
