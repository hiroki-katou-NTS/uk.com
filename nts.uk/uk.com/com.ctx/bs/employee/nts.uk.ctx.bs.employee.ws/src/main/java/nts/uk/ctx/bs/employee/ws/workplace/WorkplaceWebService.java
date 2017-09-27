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
import nts.uk.ctx.bs.employee.app.command.workplace.config.WorkplaceConfigCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.config.SaveWorkplaceConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.find.workplace.WorkplaceConfigFinder;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceCommandDto;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceConfigDto;

/**
 * The Class WorkplaceWebService.
 */
@Path("bs/employee/workplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceWebService extends WebService {

	/** The wkp config finder. */
	@Inject
	private WorkplaceConfigFinder wkpConfigFinder;
	
	/** The register workplace config command handler. */
	@Inject
	private SaveWorkplaceConfigCommandHandler registerWorkplaceConfigCommandHandler;
	
	/** The register workplace command handler. */
	@Inject
	private RegisterWorkplaceCommandHandler registerWorkplaceCommandHandler;
	
	/** The update workplace command handler. */
	@Inject
	private UpdateWorkplaceCommandHandler updateWorkplaceCommandHandler;

	/**
	 * Config hist.
	 *
	 * @param dto the dto
	 * @return the workplace config dto
	 */
	@Path("configHist")
	@POST
	public WorkplaceConfigDto configHist(WorkplaceCommandDto dto) {
		return this.wkpConfigFinder.findAllByCompanyId();
	}
	
	/**
	 * Find last config.
	 *
	 * @param dto the dto
	 * @return the workplace config dto
	 */
	@Path("findLastConfig")
	@POST
	public WorkplaceConfigDto findLastConfig(WorkplaceCommandDto dto) {
		return this.wkpConfigFinder.findLastestByCompanyId();
	}
	
	/**
	 * Register wkp config.
	 *
	 * @param command the command
	 */
	@Path("registerConfig")
	@POST
	public void registerWkpConfig(WorkplaceConfigCommand command) {
		this.registerWorkplaceConfigCommandHandler.handle(command);
	}
	
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
