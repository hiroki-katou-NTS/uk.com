/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.guideline;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.CommonGuidelineSettingCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.SaveCommonGuidelineSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.shift.estimate.guideline.CommonGuidelineSettingFinder;
import nts.uk.ctx.at.schedule.app.shift.estimate.guideline.dto.CommonGuidelineSettingDto;

/**
 * The Class CommonGuidelineSettingWs.
 */
@Path("ctx/at/schedule/shift/estimate/guideline")
@Produces("application/json")
public class CommonGuidelineSettingWs extends WebService {

	/** The common guideline setting finder. */
	@Inject
	private CommonGuidelineSettingFinder commonGuidelineSettingFinder;

	/** The save command handler. */
	@Inject
	private SaveCommonGuidelineSettingCommandHandler saveCommandHandler;

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(CommonGuidelineSettingCommand command) {
		this.saveCommandHandler.handle(command);
	}

	/**
	 * Gets the by company id.
	 *
	 * @return the by company id
	 */
	@POST
	@Path("find")
	public CommonGuidelineSettingDto getByCompanyId() {
		return this.commonGuidelineSettingFinder.findByCompanyId();
	}

}
