package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.taskmaster;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.task.*;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Path("at/shared/scherec/taskmanagement/taskmaster")
@Produces("application/json")
public class TaskMasterWebService extends WebService {
    @Inject
    private GetAvailableTaskMasterQuery getAvailableTaskMasterQuery;

    @POST
    @Path("tasks")
    public List<TaskMasterDto> getListTask(TaskDto request) {
        val taskFrameNo = Collections.singletonList(new TaskFrameNo(request.getTaskFrameNo()));
        val data = getAvailableTaskMasterQuery.getListTask(request.getBaseDate(), taskFrameNo);

        return data.stream().map(e ->
                new TaskMasterDto(
                        e.getCode().v(),
                        e.getDisplayInfo().getTaskName().v(),
                        e.getDisplayInfo().getTaskAbName().v(),
                        e.getExpirationDate().start(),
                        e.getExpirationDate().end(),
                        e.getDisplayInfo().getTaskNote().isPresent() ? e.getDisplayInfo().getTaskNote()
                                .get().v() : null
                )
        ).collect(Collectors.toList());
    }
}
