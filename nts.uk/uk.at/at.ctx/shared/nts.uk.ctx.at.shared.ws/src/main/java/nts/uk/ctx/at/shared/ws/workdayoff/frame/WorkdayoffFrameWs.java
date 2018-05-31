/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workdayoff.frame;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.workdayoff.frame.WorkdayoffFrameSaveCommand;
import nts.uk.ctx.at.shared.app.command.workdayoff.frame.WorkdayoffFrameSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFinder;

/**
 * The Class WorkdayoffFrameWs.
 */
@Path("at/shared/workdayoffframe")
@Produces(MediaType.APPLICATION_JSON)
public class WorkdayoffFrameWs extends WebService {
	
	/** The finder. */
	@Inject
	private WorkdayoffFrameFinder finder;
	
	/** The save handler. */
	@Inject
	private WorkdayoffFrameSaveCommandHandler saveHandler;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<WorkdayoffFrameFindDto> findAll() {
		return this.finder.findAll();
	}

	/**
	 * Find all used.
	 *
	 * @return the list
	 */
	@Path("findall/used")
	@POST
	public List<WorkdayoffFrameFindDto> findAllUsed() {
		return this.finder.findAllUsed();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(WorkdayoffFrameSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
