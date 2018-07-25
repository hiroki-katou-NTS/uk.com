/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.dailyworkschedule.scrA;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA.WorkScheduleOutputConditionDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA.WorkScheduleOutputConditionFinder;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrB.ErrorAlarmCodeDto;

/**
 * The Class WorkScheduleOutputConditionWS.
 * @author HoangDD
 */
@Path("at/function/dailyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class WorkScheduleOutputConditionWS extends WebService{
	
	/** The work schedule output condition finder. */
	@Inject
	private WorkScheduleOutputConditionFinder workScheduleOutputConditionFinder;
	
	/**
	 * Find.
	 *
	 * @param isExistWorkScheduleOutputCondition the is exist work schedule output condition
	 * @return the work schedule output condition dto
	 */
	@Path("startPage/{isExistWorkScheduleOutputCondition}")
	@POST
	public WorkScheduleOutputConditionDto find(@PathParam("isExistWorkScheduleOutputCondition") boolean isExistWorkScheduleOutputCondition){
		return this.workScheduleOutputConditionFinder.startScr(isExistWorkScheduleOutputCondition);
	}
	
	/**
	 * Gets the error alarm code.
	 *
	 * @return the error alarm code
	 */
	@Path("getErrorAlarmCode")
	@POST
	public List<ErrorAlarmCodeDto> getErrorAlarmCode(){
		return this.workScheduleOutputConditionFinder.getErrorAlarmCodeDto();
	}
}
