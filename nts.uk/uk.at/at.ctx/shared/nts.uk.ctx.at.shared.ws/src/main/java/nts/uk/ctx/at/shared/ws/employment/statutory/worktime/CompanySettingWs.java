/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.employment.statutory.worktime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.command.CompanySettingSaveCommand;
import nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.command.CompanySettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.find.CompanySettingDto;
import nts.uk.ctx.at.shared.app.employment.statutory.worktime.company.find.CompanySettingFinder;

/**
 * The Class CompanySettingWs.
 */
@Path("ctx/at/shared/employment/statutory/worktime/company")
@Produces("application/json")
public class CompanySettingWs extends WebService {
	
	/** The save. */
	@Inject
	private CompanySettingSaveCommandHandler handler;
	
	/** The finder. */
	@Inject
	private CompanySettingFinder finder;
	
	/**
	 * Find.
	 *
	 * @param year the year
	 * @return the company setting dto
	 */
	@POST
	@Path("find/{year}")
	public CompanySettingDto find(@PathParam("year") int year) {
		return this.finder.find(year);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(CompanySettingSaveCommand command) {
		this.handler.handle(command);
	}
}
