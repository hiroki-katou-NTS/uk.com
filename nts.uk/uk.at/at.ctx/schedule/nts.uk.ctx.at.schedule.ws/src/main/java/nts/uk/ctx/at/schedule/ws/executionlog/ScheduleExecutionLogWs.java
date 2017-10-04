/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.executionlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleExecutionLogSaveCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleExecutionLogSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleExecutionLogSaveRespone;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.export.ExeErrorLogExportService;

/**
 * The Class ScheduleExecutionLogWs.
 */
@Path("at/schedule/exelog")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleExecutionLogWs extends WebService {

	/** The schedule execution log finder. */
	@Inject
	private ScheduleExecutionLogFinder scheduleExecutionLogFinder;
	
	/** The save. */
	@Inject
	private ScheduleExecutionLogSaveCommandHandler save;

	@Inject
	private ExeErrorLogExportService exeErrorLogExportService;

	/**
	 * Find all exe log.
	 *
	 * @return the schedule execution log dto
	 */
	@POST
	@Path("findAll")
	public List<ScheduleExecutionLogDto> findAllExeLog(PeriodObject periodObj) {
		return this.scheduleExecutionLogFinder.findByDate(periodObj);
	}
	
	/**
	 * Find by id.
	 *
	 * @param executionId the execution id
	 * @return the schedule execution log dto
	 */
	@POST
	@Path("findById/{executionId}")
	public ScheduleExecutionLogDto findById(@PathParam("executionId") String executionId) {
		return this.scheduleExecutionLogFinder.findById(executionId);
	}

	/**
	 * Export error.
	 *
	 * @param executionId the execution id
	 */
	@POST
	@Path("error/export/{executionId}")
	public ExportServiceResult exportError(@PathParam("executionId") String executionId) {
		return this.exeErrorLogExportService.start(executionId);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 * @return the java type result
	 */
	@POST
	@Path("save")
	public JavaTypeResult<ScheduleExecutionLogSaveRespone> save(
			ScheduleExecutionLogSaveCommand command) {
		return new JavaTypeResult<ScheduleExecutionLogSaveRespone>(this.save.handle(command));
	}
}
