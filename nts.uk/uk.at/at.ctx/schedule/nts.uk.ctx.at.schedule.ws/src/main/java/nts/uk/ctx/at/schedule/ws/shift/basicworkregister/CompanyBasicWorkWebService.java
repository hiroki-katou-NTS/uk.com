/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.basicworkregister;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.CompanyBWSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.CompanyBWSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.CompanyBasicWorkFinder;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.CompanyBasicWorkFindDto;

/**
 * The Class CompanyBasicWorkWebService.
 */
@Path("ctx/at/schedule/shift/basicworkregister/companybasicwork/")
@Produces("application/json")
public class CompanyBasicWorkWebService extends WebService {

	/** The finder. */
	@Inject
	private CompanyBasicWorkFinder finder;

	/** The save. */
	@Inject
	private CompanyBWSaveCommandHandler save;

	/**
	 * Find all.
	 *
	 * @param workdayDivision
	 *            the workday division
	 * @return the company basic work find dto
	 */
	@POST
	@Path("find")
	public CompanyBasicWorkFindDto findAll() {
		return this.finder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(CompanyBWSaveCommand command) {
		this.save.handle(command);
	}
}
