/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.annualpaidleave;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.AnnualPaidLeaveUpateCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.AnnualPaidLeaveUpateCommandHandler;

/**
 * The Class AnnualPaidLeaveWs.
 */
@Path("ctx/pr/core/vacation/setting/annualpaidleave/")
@Produces("application/json")
public class AnnualPaidLeaveWs extends WebService {

	/** The annual paid handler. */
	@Inject
	AnnualPaidLeaveUpateCommandHandler annualPaidHandler;

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(AnnualPaidLeaveUpateCommand command) {
		this.annualPaidHandler.handle(command);
	}
}
