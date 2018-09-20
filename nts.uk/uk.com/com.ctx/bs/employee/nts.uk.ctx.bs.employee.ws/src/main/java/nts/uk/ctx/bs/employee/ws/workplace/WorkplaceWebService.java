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
import nts.uk.ctx.bs.employee.app.command.workplace.DeleteWorkplaceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.DeleteWorkplaceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWorkplaceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWorkplaceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWorkplaceHierarchyCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.SaveWorkplaceHierarchyCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.history.DeleteWkpHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.history.DeleteWkpHistoryCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.history.SaveWkpHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.history.SaveWkpHistoryCommandHandler;
import nts.uk.ctx.bs.employee.app.find.workplace.BSWorkplaceFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.affiliate.AffWorlplaceHistItemFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceDto;

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

	/** The save wkp command handler. */
	@Inject
	private SaveWorkplaceCommandHandler saveWkpCommandHandler;

	/** The delete wkp command handler. */
	@Inject
	private DeleteWorkplaceCommandHandler deleteWkpCommandHandler;
	
	/** The save wkp hierarchy command handler. */
	@Inject
	private SaveWorkplaceHierarchyCommandHandler saveWkpHierarchyCommandHandler;

	/**
	 * Adds the workplace history.
	 *
	 * @param command
	 *            the command
	 */
	@Path("hist/add")
	@POST
	public void addWorkplaceHistory(SaveWkpHistoryCommand command) {
		this.saveWkpHistoryCommandHandler.handle(command);
	}

	/**
	 * Gets the list workplace history.
	 *
	 * @param wkpId
	 *            the wkp id
	 * @return the list workplace history
	 */
	@Path("hist/{wkpId}")
	@POST
	public WorkplaceDto getListWorkplaceHistory(@PathParam("wkpId") String wkpId) {
		return this.workplaceFinder.findByWorkplaceId(wkpId);
	}

	/**
	 * Save wkp history.
	 *
	 * @param command
	 *            the command
	 */
	@Path("history/save")
	@POST
	public void saveWkpHistory(SaveWkpHistoryCommand command) {
		this.saveWkpHistoryCommandHandler.handle(command);
	}

	/**
	 * Removes the wkp history.
	 *
	 * @param command
	 *            the command
	 */
	@Path("history/remove")
	@POST
	public void removeWkpHistory(DeleteWkpHistoryCommand command) {
		this.deleteWkpHistoryHandler.handle(command);
	}

	/**
	 * Save workplace.
	 *
	 * @param command
	 *            the command
	 */
	@Path("save")
	@POST
	public void saveWorkplace(SaveWorkplaceCommand command) {
		this.saveWkpCommandHandler.handle(command);
	}

	/**
	 * Delete workplace.
	 *
	 * @param command
	 *            the command
	 */
	@Path("remove")
	@POST
	public void deleteWorkplace(DeleteWorkplaceCommand command) {
		this.deleteWkpCommandHandler.handle(command);
	}

	/**
	 * Update tree.
	 */
	@Path("updateTree")
	@POST
	public void updateTree(SaveWorkplaceHierarchyCommand command) {
		this.saveWkpHierarchyCommandHandler.handle(command);
	}

}
