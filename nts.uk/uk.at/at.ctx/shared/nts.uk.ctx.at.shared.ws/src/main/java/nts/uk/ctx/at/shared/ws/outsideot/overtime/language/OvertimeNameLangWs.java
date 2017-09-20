/* * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot.overtime.language;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.outsideot.overtime.language.OvertimeNameLangSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.overtime.language.OvertimeNameLangSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OvertimeNameLangDto;
import nts.uk.ctx.at.shared.app.find.outsideot.overtime.language.OvertimeNameLangFinder;

/**
 * The Class OvertimeLangNameWs.
 */
@Path("ctx/at/shared/outsideot/overtime/name/language")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeNameLangWs {

	/** The finder. */
	@Inject
	private OvertimeNameLangFinder  finder;
	
	/** The save. */
	@Inject
	private OvertimeNameLangSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	@POST
	@Path("findAll/{languageId}")
	public List<OvertimeNameLangDto> findAll(@PathParam("languageId") String languageId) {
		return this.finder.findAll(languageId);
	}
		
	/**
	 * Save all.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveAll")
	public void saveAll(OvertimeNameLangSaveCommand command) {
		this.save.handle(command);
	}
}

