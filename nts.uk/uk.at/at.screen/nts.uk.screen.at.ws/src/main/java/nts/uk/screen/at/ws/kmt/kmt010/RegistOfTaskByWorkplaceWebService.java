package nts.uk.screen.at.ws.kmt.kmt010;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmt010.TaskByWorkPlaceDto;
import nts.uk.screen.at.app.kmt010.TaskByWorkPlaceProcessStartupScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/shared/scherec/taskmanagement/task/kmt010")
@Produces("application/json")
public class RegistOfTaskByWorkplaceWebService extends WebService {
    @Inject
    private TaskByWorkPlaceProcessStartupScreenQuery startupScreenQuery;

    @POST
    @Path("init")
    public TaskByWorkPlaceDto getData() {
        return startupScreenQuery.getData();
    }
}
