/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.workfixed;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.workfixed.WorkFixedCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workfixed.WorkFixedCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.PersonInfoWorkFixedFinder;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkFixedFinder;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkFixedFinderDto;


/**
 * The Class WorkfixedWebService.
 */
@Path("at/record/workfixed/")
@Produces("application/json")
public class WorkfixedWebService extends WebService{

	/** The work fixed save command handler. */
	@Inject
	private WorkFixedCommandHandler workFixedCommandHandler;
	
	/** The person info work fixed finder. */
	@Inject
	private PersonInfoWorkFixedFinder personInfoWorkFixedFinder;
	
	/** The work fixed finder. */
	@Inject
	private WorkFixedFinder workFixedFinder;
	
	/**
	 * Gets the person info by person id.
	 *
	 * @param personId the person id
	 * @return the person info by person id
	 */
	@Path("personInfo/{personId}")
	@POST
	public void getPersonInfoByPersonId(@PathParam("personId") String personId) {
		this.personInfoWorkFixedFinder.getPersonInfo(personId);
	}
	
	/**
	 * Find work fixed by wkp id and closure id.
	 *
	 * @param dto the dto
	 * @return the work fixed finder dto
	 */
	@Path("findWorkFixed")
	@POST
	public WorkFixedFinderDto findWorkFixedByWkpIdAndClosureId(WorkFixedFinderDto dto) {
		return this.workFixedFinder.findWorkFixedByWkpIdAndClosureId(dto.getWkpId(), dto.getClosureId());
	}
	
	/**
	 * Find work fixed info.
	 *
	 * @param listDto the list dto
	 * @return the list
	 */
	@Path("find")
	@POST
	public List<WorkFixedFinderDto> findWorkFixedInfo(List<WorkFixedFinderDto> listDto) {
		return this.workFixedFinder.findWorkFixedInfo(listDto);
	}
	
	/**
	 * Save work fixed info.
	 *
	 * @param listCommand the list command
	 */
	@Path("save")
	@POST
	public void saveWorkFixedInfo(List<WorkFixedCommand> listCommand) {
		listCommand.stream()
			.forEach(this.workFixedCommandHandler::handle);
	}
}
