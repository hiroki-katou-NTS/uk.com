package nts.uk.ctx.alarm;

import nts.uk.ctx.alarm.byemployee.execute.ExecuteAlarmListByEmployeeCommand;
import nts.uk.ctx.alarm.byemployee.execute.ExecuteAlarmListByEmployeeCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/alarm")
@Produces(MediaType.APPLICATION_JSON)
public class ExecuteAlarmListWebService {

    @Inject
    private ExecuteAlarmListByEmployeeCommandHandler executeEmployeeHandler;

    @POST
    @Path("employee")
    public void executeByEmployee() {
        executeEmployeeHandler.handle(new ExecuteAlarmListByEmployeeCommand());
    }
}
