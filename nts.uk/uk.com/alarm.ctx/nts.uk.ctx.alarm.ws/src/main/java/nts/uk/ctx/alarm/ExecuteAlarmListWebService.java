package nts.uk.ctx.alarm;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.alarm.byemployee.execute.ExecuteAlarmListByEmployeeCommand;
import nts.uk.ctx.alarm.byemployee.execute.ExecuteAlarmListByEmployeeCommandHandler;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/alarm")
@Produces(MediaType.APPLICATION_JSON)
public class ExecuteAlarmListWebService {

    @Inject
    private ExecuteAlarmListByEmployeeCommandHandler executeEmployeeHandler;

    @POST
    @Path("employee")
    public AsyncTaskInfo executeByEmployee() {
        return executeEmployeeHandler.handle(new ExecuteAlarmListByEmployeeCommand());
    }
}
