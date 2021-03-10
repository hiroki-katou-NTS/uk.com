package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.taskassign.taskassignemployee;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskassign.taskassignemployee.DeleteTaskAssignEmployeeCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskassign.taskassignemployee.RegisterTaskAssignEmployeeCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployeeCommand;
import nts.uk.ctx.at.shared.app.find.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployeeDto;
import nts.uk.ctx.at.shared.app.find.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployeeFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("at/shared/scherec/taskmanagement/taskassign/employee")
@Produces(MediaType.APPLICATION_JSON)
public class TaskAssignEmployeeWebService extends WebService {
    @Inject
    private TaskAssignEmployeeFinder finder;

    @Inject
    private RegisterTaskAssignEmployeeCommandHandler registerCommandHandler;

    @Inject
    private DeleteTaskAssignEmployeeCommandHandler deleteCommandHandler;

    @Path("find")
    @POST
    public List<TaskAssignEmployeeDto> get(Map<String, String> params) {
        if (params != null) {
            int taskFrameNo = Integer.parseInt(params.get("taskFrameNo"));
            String taskCode = params.get("taskCode");
            return finder.get(taskFrameNo, taskCode);
        }
        return Collections.emptyList();
    }

    @Path("findAlreadySet")
    @POST
    public List<String> getAlreadySet(Map<String, String> params) {
        if (params != null) {
            int taskFrameNo = Integer.parseInt(params.get("taskFrameNo"));
            return finder.getAlreadySetList(taskFrameNo);
        }
        return Collections.emptyList();
    }

    @Path("register")
    @POST
    public void register(TaskAssignEmployeeCommand command) {
        registerCommandHandler.handle(command);
    }

    @Path("delete")
    @POST
    public void delete(TaskAssignEmployeeCommand command) {
        deleteCommandHandler.handle(command);
    }
}
