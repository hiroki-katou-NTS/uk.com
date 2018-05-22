package nts.uk.ctx.at.request.ws.application.remainingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.CheckFuncDataCommand;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.CheckFuncExecutionRespone;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.ErrorInfoExportService;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.OutputErrorInfoCommand;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.RemainingNumberExportExcel;
import nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc.SyncCheckFuncDataCommandHandler;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;

@Path("at/request/application/remainnumber/checkFunc")
@Produces("application/json")
public class CheckFunctionWebService extends WebService{

	@Inject
	private SyncCheckFuncDataCommandHandler checkFuncDataCommandHandler;
	
	@Inject
	private ErrorInfoExportService exportService;
	
	@Inject
	private RemainingNumberExportExcel remainingNumberExportExcel;
	
	@POST
	@Path("execution")
	public CheckFuncExecutionRespone csvImportProcess(CheckFuncDataCommand command) {
		CheckFuncExecutionRespone response = new CheckFuncExecutionRespone();
		response.taskInfor = checkFuncDataCommandHandler.handle(command);
		return response;
	}
	
	@POST
	@Path("export")
	public ExportServiceResult exportCsvError(List<OutputErrorInfoCommand> command) {
		return this.exportService.start(command);
	}
	
	@POST
	@Path("exportExcel")
	public ExportServiceResult exportExcel(List<ExcelInforCommand> command) {
		return this.remainingNumberExportExcel.start(command);
	}
}
