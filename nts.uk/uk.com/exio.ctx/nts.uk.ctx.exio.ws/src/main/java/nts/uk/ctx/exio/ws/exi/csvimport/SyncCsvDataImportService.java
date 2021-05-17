package nts.uk.ctx.exio.ws.exi.csvimport;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.csvimport.CsvImportDataCommand;
import nts.uk.ctx.exio.app.command.exi.csvimport.AsyncCsvExecuteImportDataCommandHandler;
import nts.uk.ctx.exio.app.command.exi.csvimport.CsvImportExecutionRespone;
import nts.uk.ctx.exio.app.command.exi.csvimport.AsyncCsvCheckImportDataCommandHandler;

@Path("exio/exi/csvimport")
@Produces("application/json")
public class SyncCsvDataImportService extends WebService {

	@Inject
	AsyncCsvCheckImportDataCommandHandler checkImportHandler;

	@Inject
	AsyncCsvExecuteImportDataCommandHandler excuteImportHandler;

	@POST
	@Path("check")
	public CsvImportExecutionRespone csvChecktProcess(CsvImportDataCommand command) {
		CsvImportExecutionRespone response = new CsvImportExecutionRespone();
		response.taskInfor = checkImportHandler.handle(command);
		return response;
	}

	@POST
	@Path("execution")
	public CsvImportExecutionRespone csvImportProcess(CsvImportDataCommand command) {
		CsvImportExecutionRespone response = new CsvImportExecutionRespone();
		response.taskInfor = excuteImportHandler.handle(command);
		return response;
	}

}
