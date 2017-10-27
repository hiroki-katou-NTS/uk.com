/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.statutory.worktime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace.WorkplaceWtSettingRemoveCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace.WorkplaceWtSettingRemoveCommandHandler;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace.WorkplaceWtSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplace.WorkplaceWtSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace.WorkplaceWtSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace.WorkplaceWtSettingFinder;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplace.WorkplaceWtSettingRequest;

/**
 * The Class WorkplaceWtSettingWs.
 */
@Path("ctx/at/shared/employment/statutory/worktime/workplace")
@Produces("application/json")
public class WorkplaceWtSettingWs extends WebService {

	/** The save handler. */
	@Inject
	private WorkplaceWtSettingSaveCommandHandler saveHandler;

	/** The remove handler. */
	@Inject
	private WorkplaceWtSettingRemoveCommandHandler removeHandler;

	/** The finder. */
	@Inject
	private WorkplaceWtSettingFinder finder;

	/**
	 * Find all.
	 *
	 * @param year the year
	 * @return the list
	 */
	@POST
	@Path("findall/{year}")
	public List<String> findAll(@PathParam("year") int year) {
		return this.finder.findAll(year);
	}
	
	/**
	 * Find.
	 *
	 * @param request the request
	 * @return the workplace wt setting dto
	 */
	@POST
	@Path("find")
	public WorkplaceWtSettingDto find(WorkplaceWtSettingRequest request) {
		return this.finder.find(request);
	}

	/**
	 * Removes the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("remove")
	public void remove(WorkplaceWtSettingRemoveCommand command) {
		this.removeHandler.handle(command);
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(WorkplaceWtSettingSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
