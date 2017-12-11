/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.flexset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.FlexWorkSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;

/**
 * The Class FlexWorkSettingWS.
 */
@Path("ctx/at/shared/worktime/flexset")
@Produces(MediaType.APPLICATION_JSON)
public class FlexWorkSettingWS extends WebService{
	
	/** The finder. */
	@Inject
	private FlexWorkSettingFinder finder;

	/**
	 * Find by company ID.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<FlexWorkSettingDto> findAll() {
		return this.finder.findAll();
	}
}
