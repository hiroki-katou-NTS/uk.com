/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.salarydetail;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.report.app.salarydetail.command.SaveSalaryPrintSettingCommand;
import nts.uk.ctx.pr.report.app.salarydetail.command.SaveSalaryPrintSettingCommandHandler;

/**
 * The Class SalaryPrintSettingWs.
 */
@Path("ctx/pr/report/salarydetail/printsetting")
@Produces("application/json")
@Stateless
public class SalaryPrintSettingWs {

	/** The save salary print setting command handler. */
	@Inject
	SaveSalaryPrintSettingCommandHandler saveSalaryPrintSettingCommandHandler;

	/**
	 * Find.
	 */
	@POST
	@Path("find")
	public void find() {
		// TODO create finder
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(SaveSalaryPrintSettingCommand command) {
		saveSalaryPrintSettingCommandHandler.handle(command);
	}
}
