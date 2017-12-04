/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime.worktimeset;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

/**
 * The Class PredTimeSetWebService.
 */
@Path("at/shared/worktimeset")
@Produces("application/json")
@Stateless
public class WorkTimeSetWebService extends WebService {

	@Inject
	private WorkTimeSettingFinder workTimeSetFinder;

	/**
	 * Find by code.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the pred dto
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
	public WorkTimeSettingDto findByCode(@PathParam("worktimeCode") String worktimeCode) {
		return this.workTimeSetFinder.findByCode(worktimeCode);
	}

}
