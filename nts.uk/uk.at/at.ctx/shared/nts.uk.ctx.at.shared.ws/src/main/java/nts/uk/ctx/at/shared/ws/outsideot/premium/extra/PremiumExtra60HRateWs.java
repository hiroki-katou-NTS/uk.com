/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot.premium.extra;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.OutsideOTBRDItemSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.OutsideOTBRDItemSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.app.find.outsideot.premium.extra.PremiumExtra60HRateFinder;

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
	private OutsideOTBRDItemSaveCommandHandler save;

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
	public void save(OutsideOTBRDItemSaveCommand command) {
		this.save.handle(command);
	}
}
