package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.operationsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.scherec.taskmanagement.operationsetting.TaskOperationSettingDto;
import nts.uk.ctx.at.shared.app.query.scherec.taskmanagement.operationsetting.TaskOperationSettingQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("at/shared/scherec/taskmanagement/operationsetting")
@Produces(MediaType.APPLICATION_JSON)
public class TaskOperationSettingWebService extends WebService {
    @Inject
    private TaskOperationSettingQuery query;

    @Path("find")
    @POST
    public TaskOperationSettingDto get() {
        return query.get();
    }
}
