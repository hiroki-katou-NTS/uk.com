package nts.uk.screen.at.ws.kbt.outputexecutionhistory;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kbt.outputexecutionhistory.GetDataToOutputCommand;
import nts.uk.screen.at.app.command.kbt.outputexecutionhistory.GetDataToOutputCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The Class OutputItemMonthlyWorkScheduleWS.
 */
@Path("screen/at/outputexechistory")
@Produces(MediaType.APPLICATION_JSON)
public class OutputExecutionHistoryWebService extends WebService {

    @Inject
    private GetDataToOutputCommandHandler getDataToOutputCommandHandler;

    @POST
    @Path("exportCSV")
    public JavaTypeResult<String> exportDataCSV(GetDataToOutputCommand command){
        this.getDataToOutputCommandHandler.handle(command);
        return new JavaTypeResult<String>("Export CSV Successfully!");
    }
}
