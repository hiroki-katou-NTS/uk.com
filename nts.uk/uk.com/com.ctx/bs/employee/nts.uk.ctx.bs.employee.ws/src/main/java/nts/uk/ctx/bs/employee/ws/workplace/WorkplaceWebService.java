/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ws.workplace;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.workplace.DeleteWkpHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.DeleteWkpHistoryCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWkpHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWkpHistoryCommandHandler;
import nts.uk.ctx.bs.employee.app.find.workplace.BSWorkplaceFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WkpInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.app.find.workplace.info.WorkplaceInfoFinder;

/**
 * The Class WorkplaceWebService.
 */
@Path("bs/employee/workplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceWebService extends WebService {
	
	/** The save wkp history command handler. */
	@Inject
	private SaveWkpHistoryCommandHandler saveWkpHistoryCommandHandler;
	
	/** The delete wkp history handler. */
	@Inject
	private DeleteWkpHistoryCommandHandler deleteWkpHistoryHandler;

	/** The workplace finder. */
	@Inject
	private BSWorkplaceFinder workplaceFinder;
	
	/** The workplace info finder. */
	@Inject
	private WorkplaceInfoFinder workplaceInfoFinder;
	
	/**
	 * Adds the workplace history.
	 *
	 * @param command the command
	 */
	@Path("hist/add")
	@POST
	public void addWorkplaceHistory(SaveWkpHistoryCommand command) {
		this.saveWkpHistoryCommandHandler.handle(command);
	}
	
	/**
	 * Gets the list workplace history.
	 *
	 * @param wkpId the wkp id
	 * @return the list workplace history
	 */
	@Path("hist/{wkpId}")
	@POST
	public WorkplaceDto getListWorkplaceHistory(@PathParam("wkpId") String wkpId) {
		return this.workplaceFinder.findByWorkplaceId(wkpId);
	}
	
	/**
	 * Gets the workplace info by history id.
	 *
	 * @param findObj the find obj
	 * @return the workplace info by history id
	 */
	@Path("findHistInfo")
	@POST
	public WorkplaceInfoDto getWorkplaceInfoByHistoryId(WkpInfoFindObject findObj) {
		return this.workplaceInfoFinder.find(findObj);
	}
	
	/**
	 * Save wkp history.
	 *
	 * @param command the command
	 */
	@Path("history/save")
	@POST
	public void saveWkpHistory(SaveWkpHistoryCommand command) {
		this.saveWkpHistoryCommandHandler.handle(command);
	}
	
	/**
	 * Removes the wkp history.
	 *
	 * @param command the command
	 */
	@Path("history/remove")
    @POST
    public void removeWkpHistory(DeleteWkpHistoryCommand command) {
        this.deleteWkpHistoryHandler.handle(command);
    }
}
