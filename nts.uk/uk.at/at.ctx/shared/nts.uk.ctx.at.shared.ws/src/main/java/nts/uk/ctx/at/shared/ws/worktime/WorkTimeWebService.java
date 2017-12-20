/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime_old.WorkTimeFinder;
import nts.uk.ctx.at.shared.app.find.worktime_old.dto.WorkTimeDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/shared/worktime")
@Produces("application/json")
public class WorkTimeWebService extends WebService {

	/** The work time finder. */
	@Inject
	private WorkTimeFinder workTimeFinder;

	/**
	 * Find by company ID.
	 *
	 * @return the list
	 */
	@POST
	@Path("findByCompanyID")
	public List<WorkTimeDto> findByCompanyID() {
		return this.workTimeFinder.findByCompanyID();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WorkTimeDto> findAll() {
		return this.workTimeFinder.findAll();
	}

	/**
	 * Find by codes.
	 *
	 * @param codes the codes
	 * @return the list
	 */
	@POST
	@Path("findByCodes")
	public List<WorkTimeDto> findByCodes(List<String> codes) {
		return this.workTimeFinder.findByCodes(codes);
	}

	/**
	 * Find by code list.
	 *
	 * @param codelist the codelist
	 * @return the list
	 */
	@POST
	@Path("findByCodeList")
	public List<WorkTimeDto> findByCodeList(List<String> codelist) {
		return this.workTimeFinder.findByCodeList(codelist);
	}

	/**
	 * Find by time.
	 *
	 * @param command the command
	 * @return the list
	 */
	@POST
	@Path("findByTime")
	public List<WorkTimeDto> findByTime(WorkTimeCommandFinder command) {
		return this.workTimeFinder.findByTime(command.codelist, command.startTime, command.endTime);
	}

	/**
	 * Find by id.
	 *
	 * @param workTimeCode the work time code
	 * @return the work time dto
	 */
	@POST
	@Path("findById/{workTimeCode}")
	public WorkTimeDto findById(@PathParam("workTimeCode") String workTimeCode){
		return this.workTimeFinder.findById(workTimeCode);
	}
}

@Data
@NoArgsConstructor
class WorkTimeCommandFinder {
	List<String> codelist;
	Integer startTime;
	Integer endTime;
}
