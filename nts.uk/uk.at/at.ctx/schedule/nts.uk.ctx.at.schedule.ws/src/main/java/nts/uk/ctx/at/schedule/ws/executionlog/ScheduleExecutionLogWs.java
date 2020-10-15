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

import lombok.val;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.schedule.app.command.executionlog.*;
import nts.uk.ctx.at.schedule.app.export.executionlog.ExeErrorLogExportService;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleCreateContentFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleCreatorFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleErrorLogFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleCreateContentDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleCreatorDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleErrorLogDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogInfoDto;
import nts.uk.ctx.at.schedule.ws.executionlog.batchserver.BatchTaskResult;
import nts.uk.shr.com.communicate.PathToWebApi;
import nts.uk.shr.com.communicate.batch.BatchServer;

/**
 * The Class ScheduleExecutionLogWs.
 */
@Path("at/schedule/exelog")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleExecutionLogWs extends WebService {

	/** The schedule execution log finder. */
	@Inject
	private ScheduleExecutionLogFinder scheduleExecutionLogFinder;
	
	/** The add. */
	@Inject
	private ScheduleCreateContentFinder scheduleCreateContentFinder;
	
	/** The schedule creator finder. */
	@Inject
	private ScheduleCreatorFinder scheduleCreatorFinder;
	
	/** The schedule error log finder. */
	@Inject
	private ScheduleErrorLogFinder scheduleErrorLogFinder;
	
	/** The add. */
	@Inject
	private ScheduleExecutionAddCommandHandler addNew;
	/** The execution. */
	@Inject
	private ScheduleCreatorExecutionCommandHandler execution;

	/** The exe error log export service. */
	@Inject
	private ExeErrorLogExportService exeErrorLogExportService;

	@Inject
	private BatchServer batchServer;
	
	@Inject
	private AsyncTaskInfoRepository asyncTaskInfoRepository;
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
	 * Find info by id.
	 *
	 * @param executionId the execution id
	 * @return the schedule execution log info dto
	 */
	@POST
	@Path("findInfoById/{executionId}")
	public ScheduleExecutionLogInfoDto findInfoById(@PathParam("executionId") String executionId) {
		return this.scheduleExecutionLogFinder.findInfoById(executionId);
	}
 
	/**
	 * Find create content by exe id.
	 *
	 * @param executionId the execution id
	 * @return the schedule create content dto
	 */
	@POST
	@Path("createContent/{executionId}")
	public ScheduleCreateContentDto findCreateContentByExeId(@PathParam("executionId") String executionId) {
		return this.scheduleCreateContentFinder.findByExecutionId(executionId);
	}
	
	/**
	 * Find all creator.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	@POST
	@Path("findAllCreator/{executionId}")
	public List<ScheduleCreatorDto> findAllCreator(@PathParam("executionId") String executionId) {
		return this.scheduleCreatorFinder.findAllByExeId(executionId);
	}
	
	/**
	 * Find all error.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	@POST
	@Path("findAllError/{executionId}")
	public List<ScheduleErrorLogDto> findAllError(@PathParam("executionId") String executionId) {
		return this.scheduleErrorLogFinder.findAllByExeId(executionId);
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
	@Path("add")
	public JavaTypeResult<ScheduleExecutionLogSaveRespone> add(
			ScheduleExecutionAddCommand command) {
		return new JavaTypeResult<ScheduleExecutionLogSaveRespone>(this.addNew.handle(command));
	}
	
	/**
	 * Execution.
	 *
	 * @param command the command
	 * @return the schedule creator execution respone
	 */
	@POST
	@Path("execution")
	public ScheduleCreatorExecutionRespone execution(ScheduleCreatorExecutionCommand command) {

		MutableValue<AsyncTaskInfo> result = new MutableValue<>();
		if (this.batchServer.exists()) {
			System.out.println("Call batch service  !");

			val webApi = this.batchServer.webApi(PathToWebApi.at("/batch/batch-schedule"),
					ScheduleCreatorExecutionCommand.class, BatchTaskResult.class);
			this.batchServer.request(webApi, c -> c.entity(command).succeeded(x -> {
				String taskId = x.getId();
				AsyncTaskInfo taskInfo = asyncTaskInfoRepository.find(taskId).get();
				result.set(taskInfo);
			}).failed(f -> {
				throw new RuntimeException(f.toString());
			}));
		} else {
			System.out.println("No call batch service !");
			result.set(this.execution.handle(command));
		}

		ScheduleCreatorExecutionRespone respone = new ScheduleCreatorExecutionRespone();
		respone.setExecuteId(command.getExecutionId());
		respone.setTaskInfor(result.get());
		return respone;

	}
	
}
