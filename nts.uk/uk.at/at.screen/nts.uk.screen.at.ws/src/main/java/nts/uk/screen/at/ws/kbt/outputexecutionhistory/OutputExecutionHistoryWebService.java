package nts.uk.screen.at.ws.kbt.outputexecutionhistory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kbt.outputexecutionhistory.GetDataToOutputCommand;
import nts.uk.screen.at.app.command.kbt.outputexecutionhistory.GetDataToOutputService;

/**
 * The Class OutputItemMonthlyWorkScheduleWS.
 */
@Path("screen/at/outputexechistory")
@Produces(MediaType.APPLICATION_JSON)
public class OutputExecutionHistoryWebService extends WebService {

	@Inject
	private GetDataToOutputService getDataToOutputService;

	@POST
	@Path("exportCSV")
	public ExportServiceResult exportDataCSV(GetDataToOutputCommand command) {
		return this.getDataToOutputService.start(command);
	}
}
