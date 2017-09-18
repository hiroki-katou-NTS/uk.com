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

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.overtime.breakdown.OvertimeBRDItemSaveCommand;
import nts.uk.ctx.at.shared.app.command.overtime.breakdown.OvertimeBRDItemSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.overtime.OvertimeBRDItemFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeBRDItemDto;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.ProductNumber;

/**
 * The Class OvertimeBRDItemWs.
 */
@Path("ctx/at/shared/overtime/breakdown")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeBRDItemWs extends WebService{

	/** The finder. */
	@Inject
	private OvertimeBRDItemFinder finder;
	
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
	public List<OvertimeBRDItemDto> findAll() {
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
	public void save(OvertimeBRDItemSaveCommand command) {
		this.save.handle(command);
	}
	
}
