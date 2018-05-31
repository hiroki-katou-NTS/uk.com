/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.frame;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.ot.frame.OvertimeWorkFrameSaveCommand;
import nts.uk.ctx.at.shared.app.command.ot.frame.OvertimeWorkFrameSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFindDto;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFinder;

/**
 * The Class OvertimeWorkFrameWs.
 */
@Path("at/shared/overtimeworkframe")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeWorkFrameWs extends WebService {
	
	/** The finder. */
	@Inject
	private OvertimeWorkFrameFinder finder;
	
	/** The save handler. */
	@Inject
	private OvertimeWorkFrameSaveCommandHandler saveHandler;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<OvertimeWorkFrameFindDto> findAll() {
		return this.finder.findAll();
	}

	/**
	 * Find all used.
	 *
	 * @return the list
	 */
	@Path("findall/used")
	@POST
	public List<OvertimeWorkFrameFindDto> findAllUsed() {
		return this.finder.findAllUsed();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(OvertimeWorkFrameSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
