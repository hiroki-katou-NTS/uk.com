/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot.breakdown.language;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.language.OutsideOTBRDItemLangSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.language.OutsideOTBRDItemLangSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.breakdown.language.OutsideOTBRDItemLangFinder;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemLangDto;


/**
 * The Class OvertimeLangBRDItemWs.
 */
@Path("ctx/at/shared/outsideot/breakdown/language")
@Produces(MediaType.APPLICATION_JSON)
public class OutsideOTBRDItemLangWs {

	/** The finder. */
	@Inject
	private OutsideOTBRDItemLangFinder finder;

	/** The save. */
	@Inject
	private OutsideOTBRDItemLangSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	@POST
	@Path("findAll/{languageId}")
	public List<OutsideOTBRDItemLangDto> findAll(@PathParam("languageId") String languageId) {
		return this.finder.findAll(languageId);
	}

	/**
	 * Save all.
	 *
	 * @param command the command
	 */
	@POST
	@Path("saveAll")
	public void saveAll(OutsideOTBRDItemLangSaveCommand command) {
		this.save.handle(command);
	}
}