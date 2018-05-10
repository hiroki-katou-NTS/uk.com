/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.dailyworkschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleCommand;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleDeleteHandler;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleSaveHandler;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputItemDailyWorkScheduleFinder;

/**
 * The Class OutputItemDailyWorkScheduleWS.
 */
@Path("at/function/dailyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class OutputItemDailyWorkScheduleWS extends WebService{
	
	/** The output item daily work schedule finder. */
	@Inject
	private OutputItemDailyWorkScheduleFinder outputItemDailyWorkScheduleFinder; 
	
	/** The output item daily work schedule save handler. */
	@Inject
	private OutputItemDailyWorkScheduleSaveHandler outputItemDailyWorkScheduleSaveHandler;
	
	/** The output item daily work schedule delete handler. */
	@Inject
	private OutputItemDailyWorkScheduleDeleteHandler outputItemDailyWorkScheduleDeleteHandler;
	
	/**
	 * Find.
	 *
	 * @return the output item daily work schedule dto
	 */
	@Path("find")
	@POST
	public OutputItemDailyWorkScheduleDto find(){
		return this.outputItemDailyWorkScheduleFinder.findByCid();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(OutputItemDailyWorkScheduleCommand command){
		this.outputItemDailyWorkScheduleSaveHandler.handle(command);
	}
	
	/**
	 * Delete.
	 *
	 * @param code the code
	 */
	@Path("delete")
	@POST
	public void delete(@PathParam("code") int code){
		this.outputItemDailyWorkScheduleDeleteHandler.delete(code);
	}
}
