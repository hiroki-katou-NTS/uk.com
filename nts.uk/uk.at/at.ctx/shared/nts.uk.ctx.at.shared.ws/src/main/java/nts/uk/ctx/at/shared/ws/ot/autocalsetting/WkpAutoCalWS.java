/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.autocalsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp.SaveWkpAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp.WkpAutoCalSetCommand;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp.WkpAutoCalSetFinder;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkp.WkpAutoCalSettingDto;

/**
 * The Class WkpAutoCalWS.
 */
@Path("ctx/at/shared/ot/autocal/wkp")
@Produces("application/json")
public class WkpAutoCalWS {

	/** The wkp auto cal set finder. */
	@Inject
	private WkpAutoCalSetFinder wkpAutoCalSetFinder;

	/** The save wkp auto cal set command handler. */
	@Inject
	private SaveWkpAutoCalSetCommandHandler saveWkpAutoCalSetCommandHandler;

	/**
	 * Gets the wkp auto cal setting dto.
	 *
	 * @param wkpId the wkp id
	 * @return the wkp auto cal setting dto
	 */
	@POST
	@Path("getautocalwkp/{wkpId}")
	public WkpAutoCalSettingDto getWkpAutoCalSettingDto(@PathParam("wkpId") String wkpId) {
		return this.wkpAutoCalSetFinder.getWkpAutoCalSetting(wkpId);
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(WkpAutoCalSetCommand command) {
		this.saveWkpAutoCalSetCommandHandler.handle(command);
	}

	/**
	 * Deledte.
	 *
	 * @param wkpId the wkp id
	 */
	@POST
	@Path("delete/{wkpId}")
	public void deledte(@PathParam("wkpId") String wkpId) {
		this.wkpAutoCalSetFinder.deleteByCode(wkpId);
	}
}
