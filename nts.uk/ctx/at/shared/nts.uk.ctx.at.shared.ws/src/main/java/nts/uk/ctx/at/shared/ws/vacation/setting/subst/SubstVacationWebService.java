/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.subst;

import java.time.DayOfWeek;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.arc.session.SessionContextProvider;

/**
 * The Class SubstVacationWebService.
 */
@Path("at/proto/substvacation/")
@Produces(MediaType.APPLICATION_JSON)
public class SubstVacationWebService extends WebService {

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 * @return the task version out model
	 */
	@POST
	@Path("com/save")
	public void save(TaskRegisterCommand command) {

	}

	/**
	 * Find task by code.
	 *
	 * @param id
	 *            the id
	 * @return the task detail dto
	 */
	@POST
	@Path("find/{id}")
	public TaskDetailDto findTaskByCode(@PathParam("id") String id) {
		return taskFinder.findById(id);
	}

	/**
	 * Gets the specification date.
	 *
	 * @return the specification date
	 */
	@POST
	@Path("find/specificationdate")
	public List<EnumConstant> getSpecificationDate() {
		return EnumAdaptor.convertToValueNameList(SpecificationDate.class);
	}

	/**
	 * Gets the day of week.
	 *
	 * @return the day of week
	 */
	@POST
	@Path("find/dayofweek")
	public List<EnumConstant> getDayOfWeek() {
		return EnumAdaptor.convertToValueNameList(DayOfWeek.class);
	}
}
