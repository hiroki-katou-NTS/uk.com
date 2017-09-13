/* * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.overtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.overtime.language.OvertimeLangNameSaveCommand;
import nts.uk.ctx.at.shared.app.command.overtime.language.OvertimeLangNameSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.overtime.OvertimeLangNameFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeLangNameDto;

/**
 * The Class OvertimeLangNameWs.
 */
@Path("ctx/at/shared/overtime/language/name")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeLangNameWs {

	/** The finder. */
	@Inject
	private OvertimeLangNameFinder  finder;
	
	/** The save. */
	@Inject
	private OvertimeLangNameSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	@POST
	@Path("findAll/{languageId}")
	public List<OvertimeLangNameDto> findAll(@PathParam("languageId") String languageId) {
		return this.finder.findAll(languageId);
	}
		
	/**
	 * Save all.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveAll")
	public void saveAll(OvertimeLangNameSaveCommand command) {
		this.save.handle(command);
	}
}

