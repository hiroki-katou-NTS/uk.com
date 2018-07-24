package nts.uk.ctx.at.record.ws.resultsperiod.optionalaggregationperiod;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.AddAggrPeriodCommand;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.AddAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.AddAggrPeriodCommandResult;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.RemoveOptionalAggrPeriodCommand;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.RemoveOptionalAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.SaveOptionalAggrPeriodCommand;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.SaveOptionalAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.UpdateOptionalAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodErrorInfoDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodErrorInfoFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodExcutionDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodTargetDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodTargetFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodExecLogDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodExecLogFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.PeriodInforDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.PeriodTargetDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.exportcsv.AggrPeriodErrorInfoExportService;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.exportcsv.AggrPeriodErrorQuery;

@Path("ctx/at/record/optionalaggr/")
@Produces("application/json")
public class OptionalAggrPeriodWs {

	@Inject
	private OptionalAggrPeriodFinder finder;
	
	@Inject
	private SaveOptionalAggrPeriodCommandHandler handler;
	
	@Inject
	private AddAggrPeriodCommandHandler executeHandler;
	
	@Inject
	private OptionalAggrPeriodExecLogFinder logFinder;
	
	@Inject
	private AggrPeriodTargetFinder targetFinder;
	
	@Inject
	private AggrPeriodErrorInfoFinder errorFinder;
	
	@Inject
	private RemoveOptionalAggrPeriodCommandHandler removeHandler;
	
	@Inject
	private AggrPeriodErrorInfoExportService exportService;
	
	@Inject
	private ExecuteAggrPeriodCommandHandler executeAggrHandler;
	
	@Inject
	private AggrPeriodErrorInfoFinder infoFinder;
	
	@Inject
	private UpdateOptionalAggrPeriodCommandHandler updateOptionalAggrPeriodCommandHandler;
	

	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<OptionalAggrPeriodDto> findAll() {
		return this.finder.findAll();
	}
	

	/**
	 * 
	 */
	@POST
	@Path("find/{aggrFrameCode}")
	public OptionalAggrPeriodDto find(@PathParam("aggrFrameCode") String aggrFrameCode) {
		return this.finder.find(aggrFrameCode);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public JavaTypeResult<AddAggrPeriodCommandResult> save(AddAggrPeriodCommand command) {
		return new JavaTypeResult<AddAggrPeriodCommandResult> (this.executeHandler.handle(command));
	}
	
	/**
	 * Update.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void update(SaveOptionalAggrPeriodCommand command) {
		this.handler.handle(command);
	}
	
	/**
	 * Update.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void delete(RemoveOptionalAggrPeriodCommand command) {
		this.removeHandler.handle(command);
	}
	
	@POST
	@Path("findbyperiod/{start}/{end}")
	public List<OptionalAggrPeriodExecLogDto> findByPeriod(@PathParam("start") String start, @PathParam("end") String end) {
		return this.logFinder.findLog(GeneralDate.fromString(start, "yyyy-MM-dd"), GeneralDate.fromString(end, "yyyy-MM-dd"));
	}
	
	@POST
	@Path("findtarget/{id}")
	public List<AggrPeriodTargetDto> findTarget(@PathParam("id") String id) {
		return this.targetFinder.findAll(id);
	}
	
	@POST
	@Path("finderrorinfo/{id}")
	public List<AggrPeriodErrorInfoDto> findErrorInfo(@PathParam("id") String id) {
		return this.errorFinder.findAll(id);
	}
	
	@POST
	@Path("findbyStatus/{start}/{end}")
	public List<OptionalAggrPeriodExecLogDto> findByStatus(@PathParam("start") String start, @PathParam("end") String end) {
		return this.logFinder.findLog(GeneralDate.fromString(end, "yyyy-MM-dd"), GeneralDate.fromString(end, "yyyy-MM-dd"));
	}
	
	@POST
	@Path("findbyStatus/{start}/{end}")
	public List<OptionalAggrPeriodExecLogDto> findBySt(@PathParam("start") String start, @PathParam("end") String end) {
		return this.logFinder.findLog(GeneralDate.fromString(end, "yyyy-MM-dd"), GeneralDate.fromString(end, "yyyy-MM-dd"));
	}
	
	/**
	 * 
	 */
	@POST
	@Path("findExecAggr/{aggrFrameCode}")
	public AggrPeriodExcutionDto findExecAggr(@PathParam("aggrFrameCode") String aggrFrameCode) {
		return this.logFinder.findAggr(aggrFrameCode);
	}

	@POST
	@Path("findTargetPeriod/{aggrId}")
	public List<PeriodTargetDto> findAllPeriod(@PathParam("aggrId") String aggrId) {
		return this.targetFinder.findAllPeriod(aggrId);
	}
	
	@POST
	@Path("findStatus/{aggrFrameCode}/{executionStatus}")
	public List<AggrPeriodExcutionDto> findStatus(@PathParam("aggrFrameCode") String aggrFrameCode,@PathParam("executionStatus") int executionStatus) {
		return this.logFinder.findStatus(aggrFrameCode, executionStatus);
	}
	
	@POST
	@Path("findAggrCode/{aggrFrameCode}")
	public List<AggrPeriodExcutionDto> findAggrCode(@PathParam("aggrFrameCode") String aggrFrameCode) {
		return this.logFinder.findAll(aggrFrameCode);
	}
	@Path("exportcsv")
	public ExportServiceResult generate(AggrPeriodErrorQuery query) {
		return this.exportService.start(query);
	}
	
	@POST
	@Path("executeAggr/{excuteId}")
	public AsyncTaskInfo executeAggr(@PathParam("excuteId") String excuteId) {
		return this.executeAggrHandler.handle(excuteId);
	}
	
	@POST
	@Path("getErrorMessageInfo/{excuteId}")
	public List<PeriodInforDto> getErrorMessageInfo(@PathParam("excuteId") String excuteId) {
		return this.infoFinder.findAllInfo(excuteId);
	}
	
	@POST
	@Path("stopExecute/{dataFromD}")
	public void stopExecute(@PathParam("dataFromD") String excuteId) {
		updateOptionalAggrPeriodCommandHandler.handle(excuteId);
	}
	
}
