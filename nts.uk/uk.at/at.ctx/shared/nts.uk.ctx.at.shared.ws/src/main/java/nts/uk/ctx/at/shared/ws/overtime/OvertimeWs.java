/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.overtime;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.overtime.OvertimeSaveCommand;
import nts.uk.ctx.at.shared.app.command.overtime.OvertimeSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.overtime.OvertimeFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeDto;

/**
 * The Class CompanySettingWs.
 */
@Path("ctx/at/shared/overtime")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeWs extends WebService {
	
	/** The finder. */
	@Inject
	private OvertimeFinder finder;
	
	/** The save. */
	@Inject
	private OvertimeSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<OvertimeDto> findAll() {
		return this.finder.findAll();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OvertimeSaveCommand command) {
		this.save.handle(command);
	}
	

}
