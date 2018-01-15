/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot.holiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.app.command.outsideot.holiday.SuperHD60HConMedSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.holiday.SuperHD60HConMedSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.app.find.outsideot.holiday.SuperHD60HConMedFinder;

/**
 * The Class SuperHD60HConMedWs.
 */
@Path("ctx/at/shared/outsideot/holiday")
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
	
	/**
	 * Find all premium rate.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/premiumExtra")
	public List<PremiumExtra60HRateDto> findAllPremiumRate() {
		return this.finder.findAll();
	}
}

