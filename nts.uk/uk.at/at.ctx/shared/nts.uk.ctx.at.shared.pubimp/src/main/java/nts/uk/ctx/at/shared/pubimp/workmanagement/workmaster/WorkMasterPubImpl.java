package nts.uk.ctx.at.shared.pubimp.workmanagement.workmaster;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskInfo;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.ExternalCorporationInfoPubExport;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.TaskDisplayInfoPubExport;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.TaskPubExport;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.WorkMasterPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Stateless
public class WorkMasterPubImpl implements WorkMasterPub {
    @Inject
    private TaskingRepository taskRepo;

    @Override
    public List<TaskPubExport> getListTask(String cid, Integer taskFrameNo, List<String> codes) {
        val lstTask = taskRepo.getListTask(cid, taskFrameNo, codes);
        if (lstTask.isEmpty()) return Collections.emptyList();

        return toTaskPubExport(lstTask);
    }

    private List<TaskPubExport> toTaskPubExport(List<TaskInfo> taskList) {
        return taskList.stream().map(e ->
                new TaskPubExport(
                        e.getCode(),
                        e.getTaskFrameNo(),
                        e.getName())
        ).collect(Collectors.toList());
    }
}
