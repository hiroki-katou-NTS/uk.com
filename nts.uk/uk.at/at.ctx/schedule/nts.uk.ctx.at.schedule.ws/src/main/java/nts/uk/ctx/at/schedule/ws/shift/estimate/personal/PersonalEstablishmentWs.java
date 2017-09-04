/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.personal;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.personal.PersonalEstablishmentDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.personal.PersonalEstablishmentDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.personal.PersonalEstablishmentSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.personal.PersonalEstablishmentSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.personal.PersonalEstablishmentFinder;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.personal.dto.PersonalEstablishmentDto;

/**
 * The Class PersonEstablishmentWs.
 */
@Path("ctx/at/schedule/shift/estimate/personal")
@Produces(MediaType.APPLICATION_JSON)
public class PersonalEstablishmentWs extends WebService{

	/** The finder. */
	@Inject
	private PersonalEstablishmentFinder finder;
	
	/** The save. */
	@Inject
	private PersonalEstablishmentSaveCommandHandler save;
	
	/** The delete. */
	@Inject
	private PersonalEstablishmentDeleteCommandHandler delete;

	/**
	 * Find by target year.
	 *
	 * @param targetYear the target year
	 * @param employeeId the employee id
	 * @return the personal establishment dto
	 */
	@POST
	@Path("find/{targetYear}/{employeeId}")
	public PersonalEstablishmentDto findByTargetYear(@PathParam("targetYear") Integer targetYear,
			@PathParam("employeeId") String employeeId) {
		return this.finder.findEstimateTime(targetYear, employeeId);
	}
	
	/**
	 * Find all by target year.
	 *
	 * @param targetYear the target year
	 * @return the list
	 */
	@POST
	@Path("findAll/{targetYear}")
	public List<PersonalEstablishmentDto> findAllByTargetYear(@PathParam("targetYear") Integer targetYear) {
		return this.finder.findAllPersonalSetting(targetYear);
	}
	
	/**
	 * Save Personal estimate.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void savePersonalEstimate(PersonalEstablishmentSaveCommand command) {
		 this.save.handle(command);
	}
	
	/**
	 * Delete personal estimate.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void deletePersonalEstimate(PersonalEstablishmentDeleteCommand command) {
		this.delete.handle(command);
	}
}
