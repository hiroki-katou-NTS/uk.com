package nts.uk.ctx.exio.ws.exi.csvimport;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.csvimport.CsvImportDataCommand;
import nts.uk.ctx.exio.app.command.exi.csvimport.SyncCsvImportDataCommandHandler;
import nts.uk.ctx.exio.app.command.exi.csvimport.csvImportExecutionRespone;
@Path("exio/exi/csvimport")
@Produces("application/json")
public class SyncCsvDataImportService extends WebService{
	
	@Inject
	SyncCsvImportDataCommandHandler importHandler;
	
	@POST
	@Path("execution")
	public csvImportExecutionRespone csvImportProcess(CsvImportDataCommand command){
		csvImportExecutionRespone response = new csvImportExecutionRespone();
		response.taskInfor = importHandler.handle(command);
		return response;
	}
}
