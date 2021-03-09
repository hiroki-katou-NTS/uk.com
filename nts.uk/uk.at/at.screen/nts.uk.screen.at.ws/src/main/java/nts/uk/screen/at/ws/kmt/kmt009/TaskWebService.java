package nts.uk.screen.at.ws.kmt.kmt009;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmt009.AcquiresSpecTaskAndSubNarrowInfoScreenQuery;
import nts.uk.screen.at.app.kmt009.InfoAcquisitionProcessStartupTaskScreenQuery;
import nts.uk.screen.at.app.kmt009.TaskDtos;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("at/shared/scherec/taskmanagement/task")
@Produces("application/json")
public class TaskWebService extends WebService {
    @Inject
    private AcquiresSpecTaskAndSubNarrowInfoScreenQuery acquiresSpecTaskAndSubNarrowInfoScreenQuery;

    @Inject
    private InfoAcquisitionProcessStartupTaskScreenQuery initScreenQuery;

    @POST
    @Path("getlistinfoandlistchildtask")
    public TaskDtos getTask(TaskParamDto param) {
        return acquiresSpecTaskAndSubNarrowInfoScreenQuery.getTask(param.getFrameNo(), param.getCode());
    }

    @POST
    @Path("init")
    public TaskDtos getTask() {
        return initScreenQuery.getDataStart();
    }
}
