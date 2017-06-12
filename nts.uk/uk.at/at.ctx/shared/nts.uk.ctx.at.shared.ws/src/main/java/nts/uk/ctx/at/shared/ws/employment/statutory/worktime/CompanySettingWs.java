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
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.EmploymentSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.EmploymentSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.EmploymentSettingFinder;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.EmploymentSettingFindDto;

/**
 * The Class CompanySettingWs.
 */
@Path("ctx/at/shared/employment/statutory/worktime/company")
@Produces("application/json")
public class CompanySettingWs extends WebService {
	
	/** The save. */
	@Inject
	private EmploymentSaveCommandHandler save;
	
	/** The finder. */
	@Inject
	private EmploymentSettingFinder finder;
	
	/**
	 * Find.
	 *
	 * @param empCode the emp code
	 * @return the employment setting find dto
	 */
	@POST
	@Path("find/{empCode}")
	public EmploymentSettingFindDto find(@PathParam("empCode") String empCode) {
		return this.finder.find(empCode);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(EmploymentSaveCommand command) {
		this.save.handle(command);
	}
}
