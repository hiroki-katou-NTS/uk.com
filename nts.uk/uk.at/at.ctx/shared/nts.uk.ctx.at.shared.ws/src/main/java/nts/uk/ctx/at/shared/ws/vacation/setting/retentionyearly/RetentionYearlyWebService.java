/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.retentionyearly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.RetentionYearlySaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.RetentionYearlySaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.RetentionYearlyFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.dto.RetentionYearlyFindDto;


/**
 * The Class RetentionYearlyWebService.
 */
@Path("ctx/at/shared/vacation/setting/retentionyearly/")
@Produces("application/json")
public class RetentionYearlyWebService extends WebService {

	/** The finder. */
	@Inject
	private RetentionYearlyFinder finder;
	
	/** The save. */
	@Inject
	private RetentionYearlySaveCommandHandler save;
	
	/**
	 * Find by id.
	 *
	 * @return the retention yearly find dto
	 */
	@POST
	@Path("find")
	public RetentionYearlyFindDto findById() {
		return this.finder.findById();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(RetentionYearlySaveCommand command) {
		this.save.handle(command);
	}
}
