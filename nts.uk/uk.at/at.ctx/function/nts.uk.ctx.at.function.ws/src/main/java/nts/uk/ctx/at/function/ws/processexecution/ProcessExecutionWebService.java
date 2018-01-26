package nts.uk.ctx.at.function.ws.processexecution;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.processexecution.ExecuteProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.ExecuteProcessExecutionCommandHandler;
import nts.uk.ctx.at.function.app.command.processexecution.RemoveProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.RemoveProcessExecutionCommandHandler;
import nts.uk.ctx.at.function.app.command.processexecution.SaveExecutionTaskSettingCommand;
import nts.uk.ctx.at.function.app.command.processexecution.SaveExecutionTaskSettingCommandHandler;
import nts.uk.ctx.at.function.app.command.processexecution.SaveProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.SaveProcessExecutionCommandHandler;
import nts.uk.ctx.at.function.app.command.processexecution.TerminateProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.TerminateProcessExecutionCommandHandler;
import nts.uk.ctx.at.function.app.find.processexecution.ExecutionTaskSettingFinder;
import nts.uk.ctx.at.function.app.find.processexecution.ProcessExecutionFinder;
import nts.uk.ctx.at.function.app.find.processexecution.ProcessExecutionLogFinder;
import nts.uk.ctx.at.function.app.find.processexecution.ProcessExecutionLogHistoryFinder;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecItemEnumDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionLogDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Path("at/function/processexec")
@Produces("application/json")
public class ProcessExecutionWebService extends WebService {
	
	/* Finder */
	@Inject
	private ProcessExecutionFinder procExecFinder;
	
	@Inject
	private ProcessExecutionLogFinder execLogFinder;
	
	@Inject
	private ExecutionTaskSettingFinder execSettingFinder;
	
	@Inject
	private ProcessExecutionLogHistoryFinder logHistFinder;
	
	/* Handler */
	@Inject
	private SaveProcessExecutionCommandHandler saveProcExecHandler;
	
	@Inject
	private RemoveProcessExecutionCommandHandler removeProcExecHandler;
	
	@Inject
	private SaveExecutionTaskSettingCommandHandler saveExecSettingHandler;
	
	/* Executor */
	@Inject
	private ExecuteProcessExecutionCommandHandler execHandler;
	
	@Inject
	private TerminateProcessExecutionCommandHandler termHandler;
	
	/** The i18n. */
	@Inject
	private I18NResourcesForUK i18n;
	
	/**
	 * Gets the enum.
	 *
	 * @return the enum
	 */
	@POST
	@Path("getEnum")
	public ExecItemEnumDto getEnum() {
		return ExecItemEnumDto.init(i18n);
	}
	
	@POST
	@Path("getProcExecList")
	public List<ProcessExecutionDto> getProcExecList() {
		return this.procExecFinder.findAll();
	}
	
	@POST
	@Path("saveProcExec")
	public JavaTypeResult<String> add(SaveProcessExecutionCommand command) {
		return new JavaTypeResult<String>(this.saveProcExecHandler.handle(command));
	}
	
	@POST
	@Path("removeProcExec")
	public void remove(RemoveProcessExecutionCommand command) {
		this.removeProcExecHandler.handle(command);
	}
	
	@POST
	@Path("getExecSetting/{execItemCd}")
	public ExecutionTaskSettingDto getExecutionSetting(@PathParam("execItemCd") String execItemCd) {
		return this.execSettingFinder.find(execItemCd);
	}
	
	@POST
	@Path("saveExecSetting")
	public JavaTypeResult<String> saveExecutionSetting(SaveExecutionTaskSettingCommand command) {
		return new JavaTypeResult<String>(this.saveExecSettingHandler.handle(command));
	}
	
	@POST
	@Path("getProcExecLogList")
	public List<ProcessExecutionLogDto> getProcExecLogList() {
		return this.execLogFinder.findAll();
	}
	
	@POST
	@Path("execute")
	public void execute(ExecuteProcessExecutionCommand command) {
		this.execHandler.handle(command);
	}
	
	@POST
	@Path("terminate")
	public void terminate(TerminateProcessExecutionCommand command) {
		this.termHandler.handle(command);
	}
	
	@POST
	@Path("getLogHistory/{execItemCd}/{execId}")
	public void getProcessExecutionLogHistory(@PathParam("execItemCd") String execItemCd, @PathParam("execId") String execId) {
		String companyId = AppContexts.user().companyId();
		this.logHistFinder.find(companyId, execItemCd, execId);
	}
}
