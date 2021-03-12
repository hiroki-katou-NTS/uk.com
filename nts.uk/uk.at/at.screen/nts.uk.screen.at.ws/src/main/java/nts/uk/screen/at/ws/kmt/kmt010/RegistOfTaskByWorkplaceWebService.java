package nts.uk.screen.at.ws.kmt.kmt010;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmt009.TaskDto;
import nts.uk.screen.at.app.kmt010.GetTaskInfoRegistSelectedTargetWplScreenQuery;
import nts.uk.screen.at.app.kmt010.TaskByWorkPlaceDto;
import nts.uk.screen.at.app.kmt010.TaskByWorkPlaceProcessStartupScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@Path("at/shared/scherec/taskmanagement/task/kmt010")
@Produces("application/json")
public class RegistOfTaskByWorkplaceWebService extends WebService {
    @Inject
    private TaskByWorkPlaceProcessStartupScreenQuery startupScreenQuery;


    @Inject
    private GetTaskInfoRegistSelectedTargetWplScreenQuery targetWplScreenQuery;

    @POST
    @Path("init")
    public TaskByWorkPlaceDto getData() {
        return startupScreenQuery.getData();
    }

    @POST
    @Path("getlistbywpl")
    public Map<Integer, List<TaskDto>> getData(PramsByWpl prams) {
        return targetWplScreenQuery.getTaskSelected(prams.getWorkPlaceId());
    }


}
