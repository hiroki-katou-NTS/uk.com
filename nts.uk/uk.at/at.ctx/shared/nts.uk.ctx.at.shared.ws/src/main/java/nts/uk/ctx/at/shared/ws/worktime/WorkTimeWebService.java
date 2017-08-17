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
import nts.uk.ctx.at.shared.app.find.worktime.WorkTimeFinder;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeScheduleDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/shared/worktime")
@Produces("application/json")
public class WorkTimeWebService extends WebService {

	@Inject
	private WorkTimeFinder workTimeFinder;

	@POST
	@Path("findByCompanyID")
	public List<WorkTimeDto> findByCompanyID() {
		return this.workTimeFinder.findByCompanyID();
	}

	@POST
	@Path("findByCodeList")
	public List<WorkTimeDto> findByCodeList(List<String> codelist) {
		return this.workTimeFinder.findByCodeList(codelist);
	}

	@POST
	@Path("findByTime")
	public List<WorkTimeDto> findByTime(WorkTimeCommandFinder command) {
		return this.workTimeFinder.findByTime(command.codelist, command.startAtr, command.startTime, command.endAtr,
				command.endTime);
	}

	@POST
	@Path("findByCIdAndDisplayAtr")
	public List<WorkTimeScheduleDto> findByCIdAndDisplayAtr() {
		return this.workTimeFinder.findByCIdAndDisplayAtr();
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
	int startAtr;
	int startTime;
	int endAtr;
	int endTime;
}
