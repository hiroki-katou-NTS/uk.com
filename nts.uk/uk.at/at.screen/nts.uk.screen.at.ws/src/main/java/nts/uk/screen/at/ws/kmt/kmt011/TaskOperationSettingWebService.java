package nts.uk.screen.at.ws.kmt.kmt011;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kmt.kmt011.TaskOperationSettingDto;
import nts.uk.screen.at.app.query.kmt.kmt011.TaskOperationSettingQuery;

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
