/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.usagesetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.usagesetting.SaveUsageSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.usagesetting.UsageSettingCommand;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.usagesetting.UsageSettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.usagesetting.dto.UsageSettingDto;

/**
 * The Class UsageSettingWs.
 */
@Path("ctx/at/schedule/shift/estimate/usagesetting")
@Produces("application/json")
public class UsageSettingWs extends WebService {

	/** The usage setting finder. */
	@Inject
	private UsageSettingFinder usageSettingFinder;

	/** The save command handler. */
	@Inject
	private SaveUsageSettingCommandHandler saveCommandHandler;

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(UsageSettingCommand command) {
		this.saveCommandHandler.handle(command);
	}

	/**
	 * Gets the detail by company id.
	 *
	 * @return the detail by company id
	 */
	@POST
	@Path("find")
	public UsageSettingDto getDetailByCompanyId() {
		return this.usageSettingFinder.findByCompanyId();
	}
	

}
