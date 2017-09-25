/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ws.workplace;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.workplace.RegisterWorkplaceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.RegisterWorkplaceCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.UpdateWorkplaceCommandHandler;

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
}
