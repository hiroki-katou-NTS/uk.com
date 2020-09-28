package nts.uk.ctx.at.function.ws.outputexecutionhistory;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.outputexecutionhistory.GetDataToOutputCommand;
import nts.uk.ctx.at.function.app.command.outputexecutionhistory.GetDataToOutputCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The Class OutputItemMonthlyWorkScheduleWS.
 */
@Path("at/function/outputexechistory")
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
