/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employment;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.employment.EmpRemoveCommand;
import nts.uk.ctx.bs.employee.app.command.employment.EmpRemoveCommandHandler;
import nts.uk.ctx.bs.employee.app.command.employment.EmpSaveCommand;
import nts.uk.ctx.bs.employee.app.command.employment.EmpSaveCommandHandler;
import nts.uk.shr.find.employment.EmploymentDto;
import nts.uk.shr.find.employment.EmploymentFinder;

/**
 * The Class EmploymentWebService.
 */
@Path("basic/company/organization/employment")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentWebService extends WebService {
	
	/** The finder. */
	@Inject
	private EmploymentFinder finder;
	
	/** The save handler. */
	@Inject
	private EmpSaveCommandHandler saveHandler;
	
	/** The remove handler. */
	@Inject
	private EmpRemoveCommandHandler removeHandler;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<EmploymentDto> findAll() {
		return this.finder.findAll();
	}
	
	
	/**
	 * Find by id.
	 *
	 * @param employmentCode the employment code
	 * @return the employment dto
	 */
	@POST
	@Path("findById/{employmentCode}")
	public EmploymentDto findById(@PathParam("employmentCode") String employmentCode) {
		return this.finder.findByCode(employmentCode);
	}
	
	/**
	 * Removes the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("remove")
	public void remove(EmpRemoveCommand command) {
		this.removeHandler.handle(command);
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(EmpSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
