/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.schedule.basicschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleUpdateCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleUpdateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.DataRegisterBasicSchedule;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.RegisterBasicScheduleCommandHandler;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;

/**
 * 
 * @author sonnh1
 *
 */
@Path("at/schedule/basicschedule")
@Produces("application/json")
public class BasicScheduleWebService extends WebService {

	@Inject
	private RegisterBasicScheduleCommandHandler registerBScheduleCommandHandler;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private BasicScheduleUpdateCommandHandler updateBScheduleCommandHandler;

	/**
	 * Register data to BASIC_SCHEDULE
	 * 
	 * @param command
	 * @return JavaTypeResult<List<String>>
	 */
	@POST
	@Path("register")
	public JavaTypeResult<List<String>> register(DataRegisterBasicSchedule command) {
		return new JavaTypeResult<List<String>>(this.registerBScheduleCommandHandler.handle(command));
	}

	/**
	 * Checks if is work time setting needed.
	 *
	 * @param workTypeCode
	 *            the work type code
	 * @return the int
	 */
	@POST
	@Path("isWorkTimeSettingNeeded/{workTypeCode}")
	public int isWorkTimeSettingNeeded(@PathParam("workTypeCode") String workTypeCode) {
		return this.basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode).value;
	}

	/**
	 * Check pair work type work time.
	 *
	 * @param workTypeCode
	 *            the work type code
	 * @param workTimeCode
	 *            the work time code
	 */
	@POST
	@Path("checkPairWorkTypeWorkTime/{workTypeCode}/{workTimeCode}")
	public void checkPairWorkTypeWorkTime(@PathParam("workTypeCode") String workTypeCode,
			@PathParam("workTimeCode") String workTimeCode) {
		this.basicScheduleService.checkPairWorkTypeWorkTime(workTypeCode, workTimeCode);
	}
	
	@POST
	@Path("checkPairWorkTypeWorkTime2/{workTypeCode}")
	public void checkPairWorkTypeWorkTime2(@PathParam("workTypeCode") String workTypeCode) {
		this.basicScheduleService.checkPairWorkTypeWorkTime(workTypeCode, null);
	}

	@POST
	@Path("update")
	public void update(BasicScheduleUpdateCommand command) {
		this.updateBScheduleCommandHandler.handle(command);
	}
}
