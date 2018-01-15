/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.worktimeset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingEnumDto;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class WorkTimeSettingWS.
 */
@Path("at/shared/worktimeset")
@Produces(MediaType.APPLICATION_JSON)
public class WorkTimeSettingWS extends WebService {

	/** The work time set finder. */
	@Inject
	private WorkTimeSettingFinder workTimeSetFinder;
	
	
	/** The i 18 n. */
	@Inject
	private I18NResourcesForUK i18n;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<SimpleWorkTimeSettingDto> findAll() {
		return this.workTimeSetFinder.findAllSimple();
	}

	/**
	 * Find by code.
	 *
	 * @param worktimeCode the worktime code
	 * @return the work time setting dto
	 */
	@POST
	@Path("findByCode/{worktimeCode}")
	public WorkTimeSettingInfoDto findByCode(@PathParam("worktimeCode") String worktimeCode) {
		return this.workTimeSetFinder.findByCode(worktimeCode);
	}
	
	/**
	 * Gets the enum.
	 *
	 * @return the enum
	 */
	@POST
	@Path("getenum")
	public WorkTimeSettingEnumDto getenum(){
		return WorkTimeSettingEnumDto.init(i18n);
	}
	
}
