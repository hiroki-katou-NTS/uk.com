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

import nts.uk.ctx.at.shared.app.command.overtime.breakdown.OvertimeBRDItemSaveCommand;
import nts.uk.ctx.at.shared.app.command.overtime.breakdown.OvertimeBRDItemSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.overtime.PremiumExtra60HRateFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.PremiumExtra60HRateDto;

/**
 * The Class PremiumExtra60HRateWs.
 */
@Path("ctx/at/shared/overtime/premium/extra")
@Produces(MediaType.APPLICATION_JSON)
public class PremiumExtra60HRateWs {

	/** The finder. */
	@Inject
	private PremiumExtra60HRateFinder finder;
	
	/** The save. */
	@Inject
	private OvertimeBRDItemSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<PremiumExtra60HRateDto> findAll() {
		return this.finder.findAll();
	}
		
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OvertimeBRDItemSaveCommand command) {
		this.save.handle(command);
	}
}
