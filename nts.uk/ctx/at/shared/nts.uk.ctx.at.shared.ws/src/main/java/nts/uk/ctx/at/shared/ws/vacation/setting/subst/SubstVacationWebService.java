/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.subst;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.ComSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.ComSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.EmpSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.EmpSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.VacationExpiration;

/**
 * The Class SubstVacationWebService.
 */
@Path("at/proto/substvacation/")
@Produces(MediaType.APPLICATION_JSON)
public class SubstVacationWebService extends WebService {

	/** The com subst vacation save command handler. */
	@Inject
	private ComSubstVacationSaveCommandHandler comSubstVacationSaveCommandHandler;

	/** The emp subst vacation save command handler. */
	@Inject
	private EmpSubstVacationSaveCommandHandler empSubstVacationSaveCommandHandler;

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 * @return the task version out model
	 */
	@POST
	@Path("com/save")
	public void save(ComSubstVacationSaveCommand command) {
		this.comSubstVacationSaveCommandHandler.handle(command);
	}

	@POST
	@Path("emp/save")
	public void save(EmpSubstVacationSaveCommand command) {
		this.empSubstVacationSaveCommandHandler.handle(command);
	}

	/**
	 * Find task by code.
	 *
	 * @param id
	 *            the id
	 * @return the task detail dto
	 */
	// @POST
	// @Path("find/{id}")
	// public TaskDetailDto findTaskByCode(@PathParam("id") String id) {
	//
	// }

	/**
	 * Gets the specification date.
	 *
	 * @return the specification date
	 */
	@POST
	@Path("find/vacationexpiration")
	public List<EnumConstant> getSpecificationDate() {
		return EnumAdaptor.convertToValueNameList(VacationExpiration.class);
	}

}
