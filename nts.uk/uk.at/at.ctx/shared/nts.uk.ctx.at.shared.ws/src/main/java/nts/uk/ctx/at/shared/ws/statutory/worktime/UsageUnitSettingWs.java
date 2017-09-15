/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.statutory.worktime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.UsageUnitSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.UsageUnitSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.UsageUnitSettingDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.UsageUnitSettingFinder;

/**
 * The Class UsageUnitSettingWs.
 */
@Path("ctx/at/shared/employment/statutory/worktime/usage/unit/setting")
@Produces("application/json")
public class UsageUnitSettingWs extends WebService {

	/** The finder. */
	@Inject
	private UsageUnitSettingFinder finder;

	/** The save. */
	@Inject
	private UsageUnitSettingSaveCommandHandler save;

	/**
	 * Find.
	 *
	 * @return the usage unit setting dto
	 */
	@POST
	@Path("find")
	public UsageUnitSettingDto find() {
		return this.finder.getSetting();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(UsageUnitSettingSaveCommand command) {
		this.save.handle(command);
	}
}
