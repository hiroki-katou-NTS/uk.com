/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.workfixed;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.workfixed.SaveWorkFixedCommand;
import nts.uk.ctx.at.record.app.command.workrecord.workfixed.SaveWorkFixedCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.PersonInfoWorkFixedDto;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkFixedFinder;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkFixedFinderDto;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkPlaceInfFinder;
import nts.uk.ctx.at.record.app.find.workrecord.workfixed.WorkPlaceInfoDto;


/**
 * The Class WorkfixedWebService.
 */
@Path("at/record/workfixed/")
@Produces("application/json")
public class WorkfixedWebService extends WebService{

	/** The work fixed command handler. */
	@Inject
	private SaveWorkFixedCommandHandler workFixedCommandHandler;
	
	/** The work fixed finder. */
	@Inject
	private WorkFixedFinder workFixedFinder;
	
	/** The work place inf finder. */
	@Inject
	private WorkPlaceInfFinder workPlaceInfFinder;
	
	
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
	public void saveWorkFixedInfo(List<SaveWorkFixedCommand> listCommand) {
		listCommand.forEach(this.workFixedCommandHandler::handle);
	}
	
	/**
	 * Find work place info.
	 *
	 * @return the list
	 */
	@Path("findWkpInfo")
	@POST
	public List<WorkPlaceInfoDto> findWorkPlaceInfo() {		
		return this.workPlaceInfFinder.findWorkPlaceInfo();
	}
	
	/**
	 * Find current person name.
	 *
	 * @return the person info work fixed dto
	 */
	@Path("currentPerson")
	@POST
	public PersonInfoWorkFixedDto findCurrentPersonName() {
		return this.workFixedFinder.findCurrentPersonName();
	}
}
