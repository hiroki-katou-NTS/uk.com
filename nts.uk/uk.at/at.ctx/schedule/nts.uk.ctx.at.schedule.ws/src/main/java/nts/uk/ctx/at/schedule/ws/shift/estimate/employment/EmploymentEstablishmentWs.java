/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.estimate.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.employment.EmploymentEstablishmentDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.employment.EmploymentEstablishmentDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.employment.EmploymentEstablishmentSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.employment.EmploymentEstablishmentSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.employment.EmploymentEstablishmentFinder;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.employment.dto.EmploymentEstablishmentDto;

/**
 * The Class EmploymentEstablishmentWs.
 */
@Path("ctx/at/schedule/shift/estimate/employment")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentEstablishmentWs extends WebService {

	/** The finder. */
	@Inject
	private EmploymentEstablishmentFinder finder;

	/** The save. */
	@Inject
	private EmploymentEstablishmentSaveCommandHandler save;
	
	/** The delete. */
	@Inject
	private EmploymentEstablishmentDeleteCommandHandler delete;

	/**
	 * Find by target year.
	 *
	 * @param targetYear the target year
	 * @return the company establishment dto
	 */
	@POST
	@Path("find/{employmentCode}/{targetYear}")
	public EmploymentEstablishmentDto findByTargetYear(
			@PathParam("employmentCode") String employmentCode,
			@PathParam("targetYear") Integer targetYear) {
		return this.finder.findEstimateTime(employmentCode, targetYear);
	}

	/**
	 * Save company estimate.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void saveEmploymentEstimate(EmploymentEstablishmentSaveCommand command) {
		this.save.handle(command);
	}
	
	/**
	 * Delete employment estimate.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void deleteEmploymentEstimate(EmploymentEstablishmentDeleteCommand command) {
		this.delete.handle(command);
	}
	
	/**
	 * Find all by target year.
	 *
	 * @param targetYear the target year
	 * @return the list
	 */
	@POST
	@Path("findAll/{targetYear}")
	public List<EmploymentEstablishmentDto> findAllByTargetYear(@PathParam("targetYear") Integer targetYear){
		return this.finder.findAllByTargetYear(targetYear).stream().map(employmentCode -> {
			EmploymentEstablishmentDto dto = new EmploymentEstablishmentDto();
			dto.setEmploymentCode(employmentCode);
			return dto;
		}).collect(Collectors.toList());
	}
}
