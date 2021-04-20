package nts.uk.screen.at.ws.kmt.kmt010;


import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.task.GetAllTaskRefinementsByWorkplaceQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskFrameUsageSettingQuery;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.screen.at.app.kmt009.TaskDto;
import nts.uk.screen.at.app.kmt010.GetTaskInfoRegistSelectedTargetWplScreenQuery;
import nts.uk.screen.at.app.kmt010.TaskByWorkPlaceProcessStartupScreenQuery;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("at/shared/scherec/taskmanagement/task/kmt010")
@Produces("application/json")
public class RegistOfTaskByWorkplaceWebService extends WebService {
    @Inject
    private TaskByWorkPlaceProcessStartupScreenQuery startupScreenQuery;

    @Inject
    private GetTaskInfoRegistSelectedTargetWplScreenQuery targetWplScreenQuery;

    @Inject
    private GetAllTaskRefinementsByWorkplaceQuery getAllQuery;

    @Inject
    private GetTaskFrameUsageSettingQuery frameUsageSettingQuery;

    @POST
    @Path("init")
    public List<TaskFrameSettingDto> getData() {
        return startupScreenQuery.getData();
    }

    @POST
    @Path("getAlreadySetWkps")
    public List<String> getAll() {
        val cid = AppContexts.user().companyId();
        val frameSetting = frameUsageSettingQuery.getWorkFrameUsageSetting(cid);
        val listFrameNo = frameSetting.getFrameSettingList().stream()
                .filter(e -> e.getUseAtr().value == UseAtr.USE.value)
                .map(e -> e.getTaskFrameNo().v()).collect(Collectors.toList());
        List<NarrowingDownTaskByWorkplace> data = getAllQuery.getListWorkByCid(cid,listFrameNo);
        return data.stream().map(NarrowingDownTaskByWorkplace::getWorkPlaceId).collect(Collectors.toList());
    }
    @POST
    @Path("getlistbywpl")
    public Map<Integer, List<TaskDto>> getData(PramsByWpl prams) {
        return targetWplScreenQuery.getTaskSelected(prams.getWorkPlaceId());
    }


}
