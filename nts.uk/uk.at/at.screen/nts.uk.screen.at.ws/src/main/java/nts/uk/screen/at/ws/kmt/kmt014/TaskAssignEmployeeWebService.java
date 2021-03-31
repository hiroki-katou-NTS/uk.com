package nts.uk.screen.at.ws.kmt.kmt014;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.query.kmt.kmt014.TaskAssignEmployeeQuery;
import nts.uk.screen.at.app.query.kmt.kmt014.TaskDto;

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
    private TaskAssignEmployeeQuery query;

    @Path("findtasks")
    @POST
    public List<TaskDto> get(Map<String, String> params) {
        if (params != null) {
            int taskFrameNo = Integer.parseInt(params.get("taskFrameNo"));
            GeneralDate baseDate = GeneralDate.fromString(params.get("baseDate"), "yyyy/MM/dd");
            return query.getTasks(taskFrameNo, baseDate);
        }
        return Collections.emptyList();
    }
}
