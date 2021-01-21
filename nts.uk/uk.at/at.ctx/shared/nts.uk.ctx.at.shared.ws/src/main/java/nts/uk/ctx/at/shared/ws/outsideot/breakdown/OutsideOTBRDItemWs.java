/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot.breakdown;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.OutsideOTBRDItemSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.breakdown.OutsideOTBRDItemSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.breakdown.OutsideOTBRDItemFinder;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;

/**
 * The Class OvertimeBRDItemWs.
 */
@Path("ctx/at/shared/outsideot/breakdown")
@Produces(MediaType.APPLICATION_JSON)
public class OutsideOTBRDItemWs extends WebService{

	/** The finder. */
	@Inject
	private OutsideOTBRDItemFinder finder;
	
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
	public List<OutsideOTBRDItemDto> findAll() {
		return this.finder.findAll();
	}
	
	/**
	 * Gets the vacation expiration enum.
	 *
	 * @return the vacation expiration enum
	 */
	@POST
	@Path("findAll/productNumber")
	public List<EnumConstant> getVacationExpirationEnum() {
		return EnumAdaptor.convertToValueNameList(ProductNumber.class);
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
