package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.exio.app.input.errors.ErrorsTextDto;
import nts.uk.ctx.exio.app.input.errors.GetLatestExternalImportErrors;
import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommand;
import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommandHandler;
import nts.uk.ctx.exio.app.input.prepare.ExternalImportPrepareCommand;
import nts.uk.ctx.exio.app.input.prepare.ExternalImportPrepareCommandHandler;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

@Path("exio/input")
@Produces("application/json")
public class ExecuteImportWebService {
	
	@Inject
	private ExternalImportPrepareCommandHandler prepare;
	
	@POST
	@Path("prepare")
	public AsyncTaskInfo prepare(ExternalImportPrepareCommand command) {
		return prepare.handle(command);
	}
	
	@Inject
	private ExternalImportExecuteCommandHandler execute;
	
	@POST
	@Path("execute")
	public AsyncTaskInfo execute(ExternalImportExecuteCommand command) {
		return execute.handle(command);
	}
	
	@Inject
	private GetLatestExternalImportErrors errors;
	
	@POST
	@Path("errors/{settingCode}/{pageNo}")
	public ErrorsTextDto getErrorsText(
			@PathParam("settingCode") String settingCode,
			@PathParam("pageNo") int pageNo) {
		
		return errors.getTextPage(new ExternalImportCode(settingCode), pageNo);
	}
}
