package nts.uk.screen.at.ws.kmt.kmt005;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameUsageSettingQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("at/shared/scherec/taskmanagement/taskframe")
@Produces(MediaType.APPLICATION_JSON)
public class TaskFrameUsageSettingWebService extends WebService {
    @Inject
    private TaskFrameUsageSettingQuery query;

    @Path("find")
    @POST
    public List<TaskFrameSettingDto> get() {
        return query.get();
    }
}
