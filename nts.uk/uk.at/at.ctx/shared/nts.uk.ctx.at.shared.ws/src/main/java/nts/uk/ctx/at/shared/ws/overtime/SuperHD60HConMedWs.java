/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.overtime.holiday.SuperHD60HConMedSaveCommand;
import nts.uk.ctx.at.shared.app.command.overtime.holiday.SuperHD60HConMedSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.overtime.SuperHD60HConMedFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.SuperHD60HConMedDto;

/**
 * The Class SuperHD60HConMedWs.
 */
@Path("ctx/at/shared/overtime/super/holiday")
@Produces(MediaType.APPLICATION_JSON)
public class SuperHD60HConMedWs {

	/** The finder. */
	@Inject
	private SuperHD60HConMedFinder  finder;
	
	/** The save. */
	@Inject
	private SuperHD60HConMedSaveCommandHandler save;

	/**
	 * Find by id.
	 *
	 * @return the super HD 60 H con med dto
	 */
	@POST
	@Path("findById")
	public SuperHD60HConMedDto findById() {
		return this.finder.findById();
	}
		
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(SuperHD60HConMedSaveCommand command) {
		this.save.handle(command);
	}
}

