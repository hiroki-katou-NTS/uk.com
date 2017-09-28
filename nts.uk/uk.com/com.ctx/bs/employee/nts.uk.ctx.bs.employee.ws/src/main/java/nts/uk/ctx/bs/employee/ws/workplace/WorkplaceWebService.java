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
import nts.uk.ctx.bs.employee.app.command.workplace.RegisterWorkplaceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.RegisterWorkplaceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.UpdateWorkplaceCommandHandler;
import nts.uk.ctx.bs.employee.app.find.workplace.BSWorkplaceFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.WorkplaceInfoFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WkpInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;

/**
 * The Class WorkplaceWebService.
 */
@Path("bs/employee/workplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceWebService extends WebService {
	
	/** The register workplace command handler. */
	@Inject
	private RegisterWorkplaceCommandHandler registerWorkplaceCommandHandler;
	
	/** The update workplace command handler. */
	@Inject
	private UpdateWorkplaceCommandHandler updateWorkplaceCommandHandler;

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
	public void addWorkplaceHistory(RegisterWorkplaceCommand command) {
		this.registerWorkplaceCommandHandler.handle(command);
	}
	
	/**
	 * Update workplace history.
	 *
	 * @param command the command
	 */
	@Path("hist/update")
	@POST
	public void updateWorkplaceHistory(RegisterWorkplaceCommand command) {
		this.updateWorkplaceCommandHandler.handle(command);
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
	@Path("find")
	@POST
	public WorkplaceInfoDto getWorkplaceInfoByHistoryId(WkpInfoFindObject findObj) {
		return this.workplaceInfoFinder.find(findObj);
	}
	
	/**
	 * Register wkp.
	 *
	 * @param command the command
	 */
	@Path("register")
	@POST
	public void registerWkp(RegisterWorkplaceCommand command) {
		this.registerWorkplaceCommandHandler.handle(command);
		;
	}
}
