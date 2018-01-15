/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.autocalsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob.RemoveWkpJobAutoCalSetCommand;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob.RemoveWkpJobAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob.SaveWkpJobAutoCalSetCommandHandler;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob.WkpJobAutoCalSetCommand;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkpjob.WkpJobAutoCalSetFinder;
import nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingDto;

/**
 * The Class WkpJobAutoCalWS.
 */
@Path("ctx/at/shared/ot/autocal/wkpjob")
@Produces("application/json")
public class WkpJobAutoCalWS {

	/** The wkp job auto cal set finder. */
	@Inject
	private WkpJobAutoCalSetFinder wkpJobAutoCalSetFinder;

	/** The save wkp job auto cal set command handler. */
	@Inject
	private SaveWkpJobAutoCalSetCommandHandler saveWkpJobAutoCalSetCommandHandler;
	
	/** The remove wkp job auto cal set command handler. */
	@Inject
	private RemoveWkpJobAutoCalSetCommandHandler removeWkpJobAutoCalSetCommandHandler;
	
	
	/**
	 * Gets the all wkp job auto cal set finder.
	 *
	 * @return the all wkp job auto cal set finder
	 */
	@POST
	@Path("getallautocalwkpjob")
	public List<WkpJobAutoCalSettingDto> getAllWkpJobAutoCalSetFinder(){
		return this.wkpJobAutoCalSetFinder.getAllWkpJobAutoCalSetting();
	}


	/**
	 * Gets the wkp job auto cal setting dto.
	 *
	 * @param wkpId the wkp id
	 * @param jobId the job id
	 * @return the wkp job auto cal setting dto
	 */
	@POST
	@Path("getautocalwkpjob/{wkpId}/{jobId}")
	public WkpJobAutoCalSettingDto getWkpJobAutoCalSettingDto(@PathParam("wkpId") String wkpId,
			@PathParam("jobId") String jobId) {
		return this.wkpJobAutoCalSetFinder.getWkpJobAutoCalSetting(wkpId, jobId);
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(WkpJobAutoCalSetCommand command) {
		this.saveWkpJobAutoCalSetCommandHandler.handle(command);
	}

	/**
	 * Deledte.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void deledte(RemoveWkpJobAutoCalSetCommand command) {
		this.removeWkpJobAutoCalSetCommandHandler.handle(command);
	}
}
