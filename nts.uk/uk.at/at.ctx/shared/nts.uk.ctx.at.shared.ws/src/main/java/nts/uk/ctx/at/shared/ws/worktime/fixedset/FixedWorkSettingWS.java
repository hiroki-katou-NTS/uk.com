/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.fixedset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.FixedWorkSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.FixedWorkSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.FixedWorkSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;

/**
 * The Class FixedWorkSettingWS.
 */
@Path("ctx/at/shared/worktime/fixedset")
@Produces(MediaType.APPLICATION_JSON)
public class FixedWorkSettingWS extends WebService {

	/** The finder. */
	@Inject
	private FixedWorkSettingFinder finder;
	
	/** The command handler. */
	@Inject
	private FixedWorkSettingSaveCommandHandler commandHandler;
	
	/**
	 * Find by code.
	 *
	 * @param worktimeCode the worktime code
	 * @return the fixed work setting dto
	 */
	@POST
	@Path("findByCode/{worktimeCode}")
	public FixedWorkSettingDto findByCode(@PathParam("worktimeCode") String worktimeCode) {
		return this.finder.findByCode(worktimeCode);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(FixedWorkSettingSaveCommand command){
		this.commandHandler.handle(command);
	}
}
